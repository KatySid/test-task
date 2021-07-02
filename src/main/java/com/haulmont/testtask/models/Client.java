package com.haulmont.testtask.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "name")
    private String name;

    @Column (name ="patronymic")
    private String patronymic;

    @Column(name = "email")
    private String email;

    @Column(name = "passport")
    private String passport;

    @Column (name ="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column (name ="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(name = "clients_banks",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id"))
    private List<Bank> banksList;

    @OneToMany(mappedBy = "client")
    private List<CreditOffer> creditOffers;

    public Client(){
        this.banksList=new ArrayList<>();

    }

    public Client(String lastName, String name, String patronymic, String email, String passport) {
        this.lastName = lastName;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.passport = passport;
        this.banksList=new ArrayList<>();
    }

    public List<Bank> getBankList(){
        return this.banksList;
    }

    @Override
    public String toString() {
        return String.format("Client [id = %d, last_name = %s, name = %d, patronymic = %s]", id, lastName, name, patronymic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addToBankList(Bank bank){
        banksList.add(bank);
    }
}