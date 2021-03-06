package com.okeydokey.journal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@ToString(exclude="format")
@NoArgsConstructor
@EqualsAndHashCode(exclude="format")
public class Journal {

    public Journal(String title, String summary, String date) throws ParseException {
        this.title = title;
        this.summary = summary;
        this.created = format.parse(date);
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    private Date created;
    private String summary;

    @JsonIgnore
    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    public String getCreatedAsShort() {
        return format.format(created);
    }

}
