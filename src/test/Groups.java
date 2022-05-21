package test;

public enum Groups {
	ADMINISTRATOR(-1), PROFESSOR(1006), BUT1(1), BUT2(16), BUT3(16), VISITOR(-1);
	
	private int level;
	
	Groups(int level) {
		if(this.name().equals("ADMINISTRATOR")) {
			level = 0;
			for(int i = 0; i < RBACPermissions.values().length; i++) {
				level += Math.pow(2,i);
			}
		}
		this.level = level;
	}

	public int getPermLevel() {
		return this.level;
	} 
}
