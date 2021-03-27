package nl.tudelft.oopp.group54.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// Determine role of student
// 1 - lecturer
// 2 - moderator
// 3 - student

@Entity
@Table(name = "Role")
public class Role {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", columnDefinition = "serial PRIMARY KEY")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(32) NOT NULL")
    private String name;

    /**
     * Empty constructor.
     */
    public Role() {

    }

    /**
     * Constructor.
     * @param id code
     * @param name description
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the Identification number of the role.
     * @return
     */
    public int getId() {
        return id;
    }


    /**
     * Updates the identification number of the role.
     * @param id new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the role.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the role.
     * @param name new name for this role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if o is of the same instance and has the same name and id.
     * @param o another object
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Role)) {
            return false;
        }

        Role role = (Role) o;

        if (getId() != role.getId()) {
            return false;
        }

        return getName().equals(role.getName());
    }

    /**
     * Computes the hashcode.
     * @return
     */
    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        return result;
    }

    /**
     * Returns a String-based representation of the role.
     * @return
     */
    @Override
    public String toString() {
        return "Role{"
                + "id=" + id
                + " - name='" + name + '\''
                + '}';
    }
}
