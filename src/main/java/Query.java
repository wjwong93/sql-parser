public abstract class Query {
    String query;

    public Query(String query) {
        this.query = query;
    }

    abstract void execute();

    @Override
    public String toString() {
        return query;
    }
}
