package com.haulmont.testtask.models;

import com.haulmont.testtask.dtos.BankDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "banks")
public class Bank {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @ManyToMany
        @JoinTable(name = "clients_banks",
                joinColumns = @JoinColumn(name = "bank_id"),
                inverseJoinColumns = @JoinColumn(name = "client_id"))
        private List<Client> clients;

        @OneToMany(mappedBy = "bank")
        private List<Credit> credits;

        @Column (name ="created_at")
        @CreationTimestamp
        private LocalDateTime createdAt;

        @Column (name ="updated_at")
        @UpdateTimestamp
        private LocalDateTime updatedAt;

        public Bank(String title) {
                this.title = title;
        }

        public Bank(BankDto bankDto){
                this.id = bankDto.getId();
                this.title = bankDto.getTitle();
        }

}

