package database;

import database.settings.Settings;
import gui.MainFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

@Data
public class MSSQLrepository implements Repository {

    private Settings settings;
    private Connection connection;

    public MSSQLrepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        String ip = (String) settings.getParameter("mssql_ip");
        String database = (String) settings.getParameter("mssql_database");
        String username = (String) settings.getParameter("mssql_username");
        String password = (String) settings.getParameter("mssql_password");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + "/" + database, username, password);
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }


    @Override
    public DefaultMutableTreeNode getSchema() {

        try {
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("HumanResources");

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), "dbo", null, tableType);

            while (tables.next()) {

                String tableName = tables.getString("TABLE_NAME");
               Entity newTable = new Entity(tableName, ir);


                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                while (columns.next()) {

                    String hasDefault = columns.getString("COLUMN_DEF");
                    String isNull = columns.getString("IS_NULLABLE");

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);

                    if (!isNull.equals(null)) {
                        AttributeConstraint attributeConstraint = new AttributeConstraint("NOT_NULL", attribute, ConstraintType.NOT_NULL);
                        attribute.add(attributeConstraint);
                        MainFrame.getInstance().getTree().updateUI();
                    }

                    ResultSet pkeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
                    ResultSet fkeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);

                    while (pkeys.next()) {
                        String pkColumnName = pkeys.getString("COLUMN_NAME");
                        if (attribute.getName().equals(pkColumnName)) {
                            AttributeConstraint attributeConstraint = new AttributeConstraint("PRIMARY_KEY", attribute, ConstraintType.PRIMARY_KEY);
                            attribute.add(attributeConstraint);
                            MainFrame.getInstance().getTree().updateUI();
                        } else continue;
                    }

                    while (fkeys.next()) {

                        String columnNamee = fkeys.getString("FKCOLUMN_NAME");
                        String originalTableName = fkeys.getString("PKTABLE_NAME");

                        if(attribute.getName().equals(columnNamee)){
                            AttributeConstraint attributeConstraint = new AttributeConstraint("FOREIGN_KEY", attribute, ConstraintType.FOREIGN_KEY);
                            attribute.add(attributeConstraint);
                            Entity relatedTable = new Entity(originalTableName, ir);


                            newTable.addRelated(relatedTable);

                        }


                    }

                    newTable.add(attribute);
                    MainFrame.getInstance().getTree().updateUI();

                }

                ir.add(newTable);
                MainFrame.getInstance().getTree().updateUI();


            }

            return ir;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }

        return null;
    }

    @Override
    public ArrayList<Row> get(String from) {

        ArrayList<Row> rows = new ArrayList<>();


        try {
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }

        return rows;
    }

    @Override
    public void add(String name, Row row) {
        try {
            this.initConnection();
            String upit = "INSERT INTO " + name;
            Map<String, Object> fields = row.getFields();
            int counter = fields.size();
            upit += " (";
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                upit = upit + entry.getKey();
                counter--;
                if (counter != 0) {
                    upit += ",";
                }
            }
            upit += ") VALUES (";

            counter = fields.size();

            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                upit += "?";
                counter--;
                if (counter != 0) {
                    upit += ",";
                }
            }

            upit += ")";
            PreparedStatement preparedStatement = connection.prepareStatement(upit);

            counter = 0;
            for(Map.Entry<String, Object> entry : fields.entrySet()) {
                preparedStatement.setObject(++counter, row.getObject(entry.getKey()));
            }

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void delete(String name, Row row) {
        try {
            this.initConnection();
            String upit = "DELETE FROM " + name + " WHERE ";
            Map<String, Object> fields = row.getFields();
            int counter = fields.size();

            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                upit = upit + entry.getKey() + " = ? ";
                counter--;
                if (counter != 0) {
                    upit += "AND ";
                }
            }
            PreparedStatement preparedStatement = connection.prepareStatement(upit);

            counter = 0;
            for(Map.Entry<String, Object> entry : fields.entrySet()) {
                preparedStatement.setObject(++counter, row.getObject(entry.getKey()));
            }

            preparedStatement.execute();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void update(String name, Row old, Row neww) {
        try {
            this.initConnection();
            String upit = "UPDATE  " + name + " SET ";
            Map<String, Object> fields = neww.getFields();
            int counter = fields.size();
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                upit = upit + entry.getKey() + " = ?";
                counter--;
                if (counter != 0) {
                    upit += ", ";
                }
            }
            upit += " WHERE ";

            counter = fields.size();

            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                upit = upit + entry.getKey() + " = ? ";
                counter--;
                if (counter != 0) {
                    upit += "AND ";
                }
            }


            PreparedStatement preparedStatement = connection.prepareStatement(upit);

            counter = 0;
            for(Map.Entry<String, Object> entry : fields.entrySet()) {
                preparedStatement.setObject(++counter, neww.getObject(entry.getKey()));
            }

            for(Map.Entry<String, Object> entry : fields.entrySet()) {
                preparedStatement.setObject(++counter, old.getObject(entry.getKey()));
            }

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public ArrayList<Row> filterAndSort(String name, ArrayList<String> filters, ArrayList<String> sorts, ArrayList<Boolean> isDesList) {
        try {
            this.initConnection();
            String upit = "SELECT ";

            int counter = filters.size();

            for (String filter : filters) {
                upit += filter;
                counter--;
                if (counter != 0) {
                    upit += ", ";
                }
            }

            upit += " FROM " + name + " ORDER BY ";

            counter = sorts.size();
            int cnt = sorts.size();
           for(int i = 0; i < counter; i++){
               upit += sorts.get(i);
               if(isDesList.get(i).equals(true)){
                   upit += " DESC";
               }
               if(isDesList.get(i).equals(false)){
                   upit += " ASC";
               }
               cnt--;
               if(cnt != 0){
                   upit += ", ";
               }
           }

            ArrayList<Row> rows = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement(upit);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Row row = new Row();
                row.setName(name);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }

        return null;
    }

	@Override
	public ArrayList<Row> count(String name, ArrayList<String> counted, ArrayList<String> forView, ArrayList<String> groupedBy) {
		try {
			this.initConnection();
			String upit = "SELECT ";
			for (String string : counted) {
				upit += "COUNT(" + string;
				upit += "), ";
			}

			int fn = forView.size();
			for (String string1 : forView) {
				upit += string1;
				fn--;
				if (fn != 0)
					upit += ", ";

			}

			upit += " FROM " + name + " GROUP BY ";

			int gn = groupedBy.size();
			for (String string2 : groupedBy) {
				upit += string2;
				gn--;
				if (fn != 0)
					upit += ", ";
			}

			ArrayList<Row> rows = new ArrayList<>();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			ResultSet rs = preparedStatement.executeQuery();

			 while (rs.next()) {

	                Row row = new Row();
	                row.setName(name);

	                ResultSetMetaData resultSetMetaData = rs.getMetaData();
	                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
	                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
	                }
	                rows.add(row);

	            }
			 return rows;

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return null;
	}

    @Override
    public ArrayList<Row> average(String name, ArrayList<String> averaged, ArrayList<String> forView, ArrayList<String> groupedBy) {
        try {
            this.initConnection();
            String upit = "SELECT ";
            for (String string : averaged) {
                upit += "AVG(" + string;
                upit += "), ";
            }

            int fn = forView.size();
            for (String string1 : forView) {
                upit += string1;
                fn--;
                if (fn != 0)
                    upit += ", ";

            }

            upit += " FROM " + name + " GROUP BY ";

            int gn = groupedBy.size();
            for (String string2 : groupedBy) {
                upit += string2;
                gn--;
                if (fn != 0)
                    upit += ", ";
            }

            ArrayList<Row> rows = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(upit);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Row row = new Row();
                row.setName(name);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }
        return null;
    }

	@Override
	public ArrayList<Row> search(String name, ArrayList<String> selectedColumns, ArrayList<String> conditions, ArrayList<String> conj) {


		try {
			this.initConnection();
			String upit = "SELECT ";
			int fn = selectedColumns.size();
			for (String string1 : selectedColumns) {
				upit += string1;
				fn--;
				if (fn != 0)
					upit += ", ";

			}

			upit += " FROM " + name + " WHERE ";
			int j = 0;
			for (int i = 0; i < conditions.size(); i++) {
				upit += conditions.get(i);
				if (j>=conj.size())
					break;
				upit += " " + conj.get(j) + " ";
				j++;
			}

			ArrayList<Row> rows = new ArrayList<>();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			ResultSet rs = preparedStatement.executeQuery();

			 while (rs.next()) {

	                Row row = new Row();
	                row.setName(name);

	                ResultSetMetaData resultSetMetaData = rs.getMetaData();
	                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
	                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
	                }
	                rows.add(row);

	            }
			 return rows;


		} catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }
		return null;
	}

}
