/*
 * UM004Repository.java
 * User Delete Repository Interface
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.repository;


public interface UM004Repository {
   /**
   * Save User Master
   * @param 
   * 
   * @return Numbers of affected rows count, -1 if error
   */
  int deleteUser(int userId, String loggedInUserName);
}
