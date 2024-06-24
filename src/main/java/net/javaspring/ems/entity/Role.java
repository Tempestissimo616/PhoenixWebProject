package net.javaspring.ems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    /**
     * 普通员工 regular, 组长 group Leader, 经理Manager, 主管Director 总裁 President
     */
    @Column(name = "name", nullable = false)
    private String name;


}
