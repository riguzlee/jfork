getPasswordAuthByUserName=select * from auth_password_view \
    where login_name=?
    
getPasswordAuthByUserId=select * from auth_password_view \
    where user_id=?

getUserLoginSecurityGroupByUserId=select group_id from user_login_security_group_view \
    where user_id =? 
    
getPermissionsBySecurityGroup=select permission_id from security_group_permission \
    where group_id=?

getPermissionsByGroupIdAndPermissionId=select permission_id from security_group_permission \
    where group_id=? and permission_id=?
    
getPermissionsByUserId=select permission_id from user_login_security_group_view g, security_group_permission p \
    where g.group_id=p.group_id and user_id=? and permission_id=?