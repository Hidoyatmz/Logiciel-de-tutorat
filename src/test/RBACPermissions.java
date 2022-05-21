package test;

public enum RBACPermissions {
	RBAC_PERM_APPLY_TUTORED(0), 		//	1
	RBAC_PERM_ACCEPT_TUTORED(1), 		//	2
	RBAC_PERM_DECLINE_TUTORED(2),		// 	4
	RBAC_PERM_VIEW_TUTORED_APPLY(3),	// 	8
	RBAC_PERM_APPLY_TUTORING(4),		// 	16
	RBAC_PERM_ACCEPT_TUTORING(5),		// 	32
	RBAC_PERM_DECLINE_TUTORING(6),		// 	64
	RBAC_PERM_VIEW_TUTORING_APPLY(7),	// 	128
	RBAC_PERM_LAUNCH_GENERATION(8),		// 	256
	RBAC_PERM_APPLY_RESSOURCE(9),		// 	512
	;
	
	private int id;
	
	RBACPermissions(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
