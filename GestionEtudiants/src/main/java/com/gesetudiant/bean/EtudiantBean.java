/**
 * 
 */
package com.gesetudiant.bean;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * 
 */
@Named
@RequestScoped
public class EtudiantBean {
	private Etudiant  etudiant;
	private List<Etudiant> listeEtudiant;
 	private boolean modif=false;
 	private static int etdId;
 	private Date date;
 	public EtudiantBean() {
	// TODO Auto-generated constructor stub
	 etudiant = new Etudiant();
	 
 	}

	//CLASS CONNECTION
	public Connection connect() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbetudiant","root","");
			return con;
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			Connection con = null;
			e.printStackTrace();
			return con;
		}catch (SQLException e) {
			// TODO: handle exception
			Connection con = null;
			e.printStackTrace();
			return con;
		}
		
	}
	public List<Etudiant> afficheEtudiant(){
		listeEtudiant = new ArrayList<Etudiant>();
		String req = "select * from etudiant";
		try {
			PreparedStatement ps = connect().prepareStatement(req);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				Etudiant e =  new Etudiant();
				e.setId(res.getInt("id"));
				e.setNom(res.getString("nom"));
				e.setPrenom(res.getString("prenom"));
				e.setDatenaiss(res.getDate("datenaiss"));
				listeEtudiant.add(e);
			}
			return listeEtudiant;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return listeEtudiant;
		}
	}
	
	public void ajoutEtudiant() {
		//la requet sql
		String req = "insert into etudiant (nom,prenom,datenaiss) value (?,?,?)";
		etudiant.setDatenaiss(convDate(date));
		try {
			//preparation de la requete
			PreparedStatement ps = connect().prepareStatement(req);
			//ajout dans la requete sql les?
			ps.setString(1, etudiant.getNom());
			ps.setString(2, etudiant.getPrenom());
			ps.setDate(3, (java.sql.Date) etudiant.getDatenaiss());
			
			//execution de la requet
			ps.execute();
			
			afficheEtudiant();
			etudiant = new Etudiant();
			date = null;
			
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	
	public void deleteEtudiant(Etudiant etd) {
		String req = "delete from etudiant where id = ?";
		try {
			PreparedStatement ps = connect().prepareStatement(req);
			ps.setInt(1, etd.getId());
			ps.execute();	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void affiche(Etudiant etd) {
		etdId = etd.getId();
		date = etd.getDatenaiss();
		etudiant = etd;
		modif=true;
	}
	public void modifEtudiant() {
		etudiant.setDatenaiss(convDate(date));
		try {
			String req = "UPDATE etudiant SET nom = ?, prenom = ?, datenaiss = ? WHERE id = ?";
			PreparedStatement ps = connect().prepareStatement(req);
			ps.setString(1, etudiant.getNom());
			ps.setString(2, etudiant.getPrenom());
			ps.setDate(3, (java.sql.Date) etudiant.getDatenaiss());
			ps.setInt(4, etdId);
			
			System.out.println(ps);
			
			ps.executeUpdate();
			
			afficheEtudiant();
			etudiant = new Etudiant();
			date = null;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public java.sql.Date convDate(java.util.Date calendarDate){
		return new java.sql.Date(calendarDate.getTime());
	}
	
	
	
	/**
	 * @return the etdId
	 */
	public int getEtdId() {
		return etdId;
	}

	/**
	 * @param etdId the etdId to set
	 */
	public void setEtdId(int etdId) {
		EtudiantBean.etdId = etdId;
	}

	/**
	 * @return the modif
	 */
	public boolean isModif() {
		return modif;
	}

	/**
	 * @param modif the modif to set
	 */
	public void setModif(boolean modif) {
		this.modif = modif;
	}

	/**
	 * @return the pisteEtudiant
	 */
	public List<Etudiant> getPisteEtudiant() {
		return afficheEtudiant();
	}
	/**
	 * @param pisteEtudiant the pisteEtudiant to set
	 */
	public void setPisteEtudiant(List<Etudiant> pisteEtudiant) {
	
	}
	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant;
	}

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	/**
	 * @return the listeEtudiant
	 */
	public List<Etudiant> getListeEtudiant() {
		return listeEtudiant;
	}
	/**
	 * @param listeEtudiant the listeEtudiant to set
	 */
	public void setListeEtudiant(List<Etudiant> listeEtudiant) {
		this.listeEtudiant = listeEtudiant;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	
	

}
