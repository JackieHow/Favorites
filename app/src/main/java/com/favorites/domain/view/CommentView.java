package com.favorites.domain.view;
/**
*@InterfaceName: CommentView
*@Description: 
*@author lyoko
*@date
*@version 1.0
*/
public interface CommentView {
	Long getUserId();
	String getUserName();
	String getProfilePicture();
	String getContent();
	Long getCreateTime();
	Long getReplyUserId();
}
