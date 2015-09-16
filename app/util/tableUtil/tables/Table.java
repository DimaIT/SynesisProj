package util.tableUtil.tables;

public abstract class Table {

    protected String name;
    protected String entityName;
    protected String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public abstract String getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (name != null ? !name.equals(table.name) : table.name != null) return false;
        return !(entityName != null ? !entityName.equals(table.entityName) : table.entityName != null);

    }

    public int getHashCode(){
        return Math.abs((short) hashCode());
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (entityName != null ? entityName.hashCode() : 0);
        return result;
    }
}
