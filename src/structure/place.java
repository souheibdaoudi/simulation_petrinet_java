package structure;

public class place {
    private int token;
    private int initialtoken;
    private int maxtoken;
	private String name ;

    public int getInitialtoken() {
		return initialtoken;
	}

	public void setInitialtoken(int initialtoken) {
		this.initialtoken = initialtoken;
	}

	public int getMaxtoken() {
		return maxtoken;
	}

	public void setMaxtoken(int maxtoken) {
		this.maxtoken = maxtoken;
	}

    
    public place(int token ,String name ) {
        this.token = token;
        this.name = name;

    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

   
}
