package test;

public class User {
	private static int id = 0;
	private final int userId;
	private Groups group;
	
	public User(Groups group) {
		this.group = group;
		this.userId = User.id;
		User.id ++;
	}
	
	public User() {
		this(Groups.VISITOR);
	}
	
	public boolean hasPermission(RBACPermissions perm) {
		if(this.group == Groups.VISITOR) return false;
		String userPerms = Integer.toBinaryString(this.group.getPermLevel());
		int permId = perm.getId();
		if(userPerms.length() < permId+1) return false;
		return userPerms.charAt(userPerms.length()-(permId+1)) == '1';
	}
	
	public String hasPermissionToString(RBACPermissions perm) {
		boolean hasPerm = this.hasPermission(perm);
		String prefix = hasPerm ? "" : "doesn't ";
		return "User n°"+ this.id +" " + prefix + "has access to " + perm.toString();
	}
	
	public static void main(String[] args) {
		User user = new User(Groups.BUT2);
		for (RBACPermissions perm : RBACPermissions.values()) { 
			System.out.println(user.hasPermissionToString(perm));
		}
	}
}
