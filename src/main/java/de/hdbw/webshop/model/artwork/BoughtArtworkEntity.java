package de.hdbw.webshop.model.artwork;

import de.hdbw.webshop.model.users.entity.AllUsersEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bought_artwork")
@Data
@NoArgsConstructor
public class BoughtArtworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "artwork_id", referencedColumnName = "id")
    private ArtworkEntity artworkEntity;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private AllUsersEntity allUsersEntity;
    @Column(name = "buying_date")
    private LocalDate localDate;

    public BoughtArtworkEntity(ArtworkEntity artworkEntity, AllUsersEntity allUsersEntity) {
        this.artworkEntity = artworkEntity;
        this.allUsersEntity = allUsersEntity;
        this.localDate = LocalDate.now();
    }
}
