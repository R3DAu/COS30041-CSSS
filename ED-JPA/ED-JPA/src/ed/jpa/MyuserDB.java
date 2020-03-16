/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jpa;

import entity.Myuser;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author reyno
 */
public class MyuserDB {

    private EntityManager em = null;

    public MyuserDB() {
        //using default persistance unit defined in the persistance.xml file
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ED-EntityPU");
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Myuser findMyuser(String userid) {
        return em.find(Myuser.class, userid);
    }

    public boolean createMyuser(Myuser myuser) throws Exception {
        try {
            if (findMyuser(myuser.getUserid()) != null) {
                //myuser already exists.
                return false;
            }

            //myuser doesn't exist
            //Start a db transaction
            em.getTransaction().begin();
            em.persist(myuser);//add the object
            em.getTransaction().commit();

            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean updateMyuser(Myuser myuser) throws Exception {
        try {
            if (findMyuser(myuser.getUserid()) == null) {
                //myuser doesn't exist.
                return false;
            }

            //myuser does exist
            //Start a db transaction
            em.getTransaction().begin();
            em.merge(myuser);//add the object
            em.getTransaction().commit();

            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean deleteMyuser(Myuser myuser) throws Exception {
        try {
            if (findMyuser(myuser.getUserid()) == null) {
                //myuser doesn't exist.
                return false;
            }

            //myuser does exist
            //Start a db transaction
            em.getTransaction().begin();
            em.remove(myuser);//add the object
            em.getTransaction().commit();

            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean createRecord(MyuserDTO myuserDTO) {
        Myuser myuser = this.myDTO2DAO(myuserDTO);

        boolean result = false;
        try {
            result = this.createMyuser(myuser);
        } catch (Exception ex) {
        }

        return result;
    }

    private Myuser myDTO2DAO(MyuserDTO myDTO) {
        Myuser myuser = new Myuser();

        myuser.setUserid(myDTO.getUserid());
        myuser.setName(myDTO.getName());
        myuser.setPassword(myDTO.getPassword());
        myuser.setEmail(myDTO.getEmail());
        myuser.setPhone(myDTO.getPhone());
        myuser.setAddress(myDTO.getAddress());
        myuser.setSecqn(myDTO.getSecQn());
        myuser.setSecans(myDTO.getSecAns());

        return myuser;
    }

    private MyuserDTO myDAO2DTO(Myuser myDAO) {
        return new MyuserDTO(myDAO.getUserid(), myDAO.getName(), myDAO.getPassword(), myDAO.getEmail(), myDAO.getPhone(), myDAO.getAddress(), myDAO.getSecqn(), myDAO.getSecans());
    }

    public MyuserDTO getRecord(String userid) {
        Myuser myuser = em.find(Myuser.class, userid);

        if (myuser != null) {
            return myDAO2DTO(myuser);
        } else {
            return null;
        }
    }

    //updates a specific entitiy...
    public boolean updateRecord(MyuserDTO myuserDTO) {
        Myuser myuser = this.myDTO2DAO(myuserDTO);

        boolean result = false;
        try {
            result = this.updateMyuser(myuser);
        } catch (Exception ex) {
        }

        return result;
    }

    //updates a specific entitiy...
    public boolean deleteRecord(String userid) {
        Myuser myuser = em.find(Myuser.class, userid);

        boolean result = false;
        try {
            result = this.deleteMyuser(myuser);
        } catch (Exception ex) {
        }
        
        return result;
    }
}
