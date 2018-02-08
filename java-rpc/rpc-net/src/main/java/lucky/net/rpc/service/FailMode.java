package lucky.net.rpc.service;


public enum FailMode {
    // FailOver selects another server automatically
    FailOver(1),
    // FailFast returns error immediately
    FailFast(2);

    private int value;

    FailMode(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
