package de.hdbw.webshop.dto.artwork;

import lombok.Getter;

import java.util.List;


@Getter
public class ShoppingCartDTO {
    private final List<ArtworkForListViewDTO> artworks;
    private final double sumBeforeTaxes;
    private final double sumAfterTaxes;

    public ShoppingCartDTO(List<ArtworkForListViewDTO> artworks) {
        this.artworks = artworks;
        this.sumBeforeTaxes =getSumBeforeTaxes(artworks);
        this.sumAfterTaxes = this.sumBeforeTaxes * 1.19;
    }

    public double getSumBeforeTaxes (List<ArtworkForListViewDTO> artworks) {
        return artworks.stream().mapToDouble(ArtworkForListViewDTO::getPrice).sum();
    }
}