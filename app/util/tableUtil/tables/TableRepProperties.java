package util.tableUtil.tables;

public class TableRepProperties {
    private boolean messageEnabled = true;
    private boolean dataTablesEnabled = true;
    private boolean filterEnabled = true;
    private boolean colvisEnabled = true;
    private boolean addLinkAbsolute = true;
    private boolean editLinkAbsolute = true;
    private boolean addLinkEnabled = true;
    private boolean editLinkEnabled = true;
    private boolean deleteLinkEnabled = true;
    private boolean inactivateLinkEnabled = false;
    private boolean chevronRightEnabled = false;
    private boolean printEnabled = true;
    private String deleteLinkUrlPostfix = "";
    private String editLinkUrlPostfix = "";
    private String addLinkUrl = "";

    public TableRepProperties() {
    }

    public TableRepProperties(boolean dataTablesEnabled, boolean filterEnabled, boolean colvisEnabled, boolean addLinkEnabled, boolean editLinkEnabled, boolean deleteLinkEnabled, boolean inactivateLinkEnabled, boolean chevronRightEnabled) {
        this.dataTablesEnabled = dataTablesEnabled;
        this.filterEnabled = filterEnabled;
        this.colvisEnabled = colvisEnabled;
        this.addLinkEnabled = addLinkEnabled;
        this.editLinkEnabled = editLinkEnabled;
        this.deleteLinkEnabled = deleteLinkEnabled;
        this.inactivateLinkEnabled = inactivateLinkEnabled;
        this.chevronRightEnabled = chevronRightEnabled;
    }

    public boolean isMessageEnabled() {
        return messageEnabled;
    }

    public TableRepProperties setMessageEnabled(boolean messageEnabled) {
        this.messageEnabled = messageEnabled;
        return this;
    }

    public static TableRepProperties getAllDisabled(){
        return new TableRepProperties(false, false, false, false, false, false, false, false);
    }

    public boolean isDataTablesEnabled() {
        return dataTablesEnabled;
    }

    public TableRepProperties setDataTablesEnabled(boolean dataTablesEnabled) {
        this.dataTablesEnabled = dataTablesEnabled;
        return this;
    }

    public boolean isFilterEnabled() {
        return filterEnabled;
    }

    public TableRepProperties setFilterEnabled(boolean filterEnabled) {
        this.filterEnabled = filterEnabled;
        return this;
    }

    public boolean isColvisEnabled() {
        return colvisEnabled;
    }

    public TableRepProperties setColvisEnabled(boolean colvisEnabled) {
        this.colvisEnabled = colvisEnabled;
        return this;
    }

    public boolean isAddLinkAbsolute() {
        return addLinkAbsolute;
    }

    public TableRepProperties addLinkAbsolute() {
        this.addLinkAbsolute = true;
        return this;
    }

    public TableRepProperties addLinkRelative() {
        this.addLinkAbsolute = false;
        return this;
    }

    public TableRepProperties deleteLinkAbsolute() {
        return this;
    }

    public TableRepProperties deleteLinkRelative() {
        return this;
    }

    public TableRepProperties inactivateLinkAbsolute() {
        return this;
    }

    public TableRepProperties inactivateLinkRelative() {
        return this;
    }

    public boolean isEditLinkAbsolute() {
        return editLinkAbsolute;
    }

    public TableRepProperties editLinkAbsolute() {
        this.editLinkAbsolute = true;
        return this;
    }

    public TableRepProperties editLinkRelative() {
        this.editLinkAbsolute = true;
        return this;
    }

    public boolean isAddLinkEnabled() {
        return addLinkEnabled;
    }

    public TableRepProperties setAddLinkEnabled(boolean addLinkEnabled) {
        this.addLinkEnabled = addLinkEnabled;
        return this;
    }

    public boolean isEditLinkEnabled() {
        return editLinkEnabled;
    }

    public TableRepProperties setEditLinkEnabled(boolean editLinkEnabled) {
        this.editLinkEnabled = editLinkEnabled;
        return this;
    }

    public boolean isDeleteLinkEnabled() {
        return deleteLinkEnabled;
    }

    public TableRepProperties setDeleteLinkEnabled(boolean deleteLinkEnabled) {
        this.deleteLinkEnabled = deleteLinkEnabled;
        return this;
    }

    public boolean isInactivateLinkEnabled() {
        return inactivateLinkEnabled;
    }

    public TableRepProperties setInactivateLinkEnabled(boolean inactivateLinkEnabled) {
        this.inactivateLinkEnabled = inactivateLinkEnabled;
        return this;
    }

    public boolean isChevronRightEnabled() {
        return chevronRightEnabled;
    }

    public TableRepProperties setChevronRightEnabled(boolean chevronRightEnabled) {
        this.chevronRightEnabled = chevronRightEnabled;
        return this;
    }

    public boolean isPrintEnabled() {
        return printEnabled;
    }

    public TableRepProperties setPrintEnabled(boolean printEnabled) {
        this.printEnabled = printEnabled;
        return this;
    }

    public String getDeleteLinkUrlPostfix() {
        return deleteLinkUrlPostfix;
    }

    public TableRepProperties setDeleteLinkUrlPostfix(String deleteLinkUrlPostfix) {
        this.deleteLinkUrlPostfix = deleteLinkUrlPostfix;
        return this;
    }

    public String getEditLinkUrlPostfix() {
        return editLinkUrlPostfix;
    }

    public TableRepProperties setEditLinkUrlPostfix(String editLinkUrlPostfix) {
        this.editLinkUrlPostfix = editLinkUrlPostfix;
        return this;
    }

    public String getAddLinkUrl() {
        return addLinkUrl;
    }

    public TableRepProperties setAddLinkUrl(String addLinkUrl) {
        this.addLinkUrl = addLinkUrl;
        return this;
    }
}
