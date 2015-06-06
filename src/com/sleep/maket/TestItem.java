
package com.sleep.maket;

import javax.persistence.*;


/**
 * Created by Артем on 04.06.2015.
 */


@Entity
@Table(name = "TB_TestItems" )
public class TestItem {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;

    public TestItem(String descr)
    {
        description = descr;
    }
}

