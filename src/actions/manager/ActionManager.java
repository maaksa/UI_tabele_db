package actions.manager;

import actions.*;

public class ActionManager {

    private AddAction addAction;
    private DeleteAction deleteAction;
    private FilterAndSortAction filterAndSortAction;
    private RefreshAction refreshAction;
    private SearchAction searchAction;
    private UpdateAction updateAction;
    private CountAction countAction;
    private AverageAction averageAction;

    public ActionManager(){
        addAction = new AddAction();
        deleteAction = new DeleteAction();
        filterAndSortAction = new FilterAndSortAction();
        refreshAction = new RefreshAction();
        searchAction = new SearchAction();
        updateAction = new UpdateAction();
        countAction = new CountAction();
        averageAction = new AverageAction();
    }

    public AddAction getAddAction() {
        return addAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public FilterAndSortAction getFilterAndSortAction() {
        return filterAndSortAction;
    }

    public RefreshAction getRefreshAction() {
        return refreshAction;
    }

    public SearchAction getSearchAction() {
        return searchAction;
    }

    public UpdateAction getUpdateAction() {
        return updateAction;
    }

	public CountAction getCountAction() {
		return countAction;
	}

    public AverageAction getAverageAction() {
        return averageAction;
    }
}
