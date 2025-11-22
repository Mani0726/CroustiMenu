package com.example.croustimenu.app.models.mapper

import com.example.croustimenu.app.models.dto.CategoriePlatDto
import com.example.croustimenu.app.models.dto.InfoJourDto
import com.example.croustimenu.app.models.dto.ListeRegionsDto
import com.example.croustimenu.app.models.dto.ListeRestaurantsDto
import com.example.croustimenu.app.models.dto.MenuDto
import com.example.croustimenu.app.models.dto.OuvertureDto
import com.example.croustimenu.app.models.dto.PlatDto
import com.example.croustimenu.app.models.dto.RegionDto
import com.example.croustimenu.app.models.dto.RepasDto
import com.example.croustimenu.app.models.dto.RestaurantDto
import com.example.croustimenu.app.models.dto.TypeRestaurantDto
import com.example.croustimenu.app.models.entities.CategoriePlat
import com.example.croustimenu.app.models.entities.InfoJour
import com.example.croustimenu.app.models.entities.ListeRegions
import com.example.croustimenu.app.models.entities.ListeRestaurants
import com.example.croustimenu.app.models.entities.MenuDuJour
import com.example.croustimenu.app.models.entities.Ouverture
import com.example.croustimenu.app.models.entities.Plat
import com.example.croustimenu.app.models.entities.Region
import com.example.croustimenu.app.models.entities.Repas
import com.example.croustimenu.app.models.entities.Restaurant
import com.example.croustimenu.app.models.entities.TypeRestaurant

class CrousMapper {
    /*
    * REGION
    */
    fun RegionDto.toDomain(): Region =
        Region(
            code = this.code,
            libelle = this.libelle
        )

    fun ListeRegionsDto.toDomain(): ListeRegions =
        ListeRegions(
            regions = this.liste_regions_dto.map { it.toDomain() }
        )

    /*
     * OUVERTURE / INFOJOUR
     */
    fun OuvertureDto.toDomain(): Ouverture =
        Ouverture(
            matin = this.matin,
            midi = this.midi,
            soir = this.soir
        )

    fun InfoJourDto.toDomain(): InfoJour =
        InfoJour(
            jour = this.jour,
            ouverture = this.ouverture_dto.toDomain()
        )

    /*
     * PLAT / CATEGORIE / REPAS / MENU
     */
    fun PlatDto.toDomain(): Plat =
        Plat(
            code = this.code,
            ordre = this.ordre,
            libelle = this.libelle
        )

    fun CategoriePlatDto.toDomain(): CategoriePlat =
        CategoriePlat(
            code = this.code,
            libelle = this.libelle,
            ordre = this.ordre,
            liste_plats = this.liste_plats_dto.map { it.toDomain() }
        )

    fun RepasDto.toDomain(): Repas =
        Repas(
            code = this.code,
            type = this.type,
            liste_categories_plat = this.liste_categories_plat_dto.map { it.toDomain() }
        )

    fun MenuDto.toDomain(): MenuDuJour = MenuDuJour(
        code = this.code,
        date = this.date,
        liste_repas = this.repasDto.map { it.toDomain() }
    )


    /*
     * TYPE RESTAURANT
     */
    fun TypeRestaurantDto.toDomain(): TypeRestaurant =
        TypeRestaurant(
            code = this.code,
            libelle = this.libelle
        )

    /*
     * RESTAURANT
     */
    fun RestaurantDto.toDomain(estFavori: Boolean = false): Restaurant =
        Restaurant(
            code = this.code,
            region = this.region.toDomain(),
            type = this.type_restaurant_dto.toDomain(),
            nom = this.nom,
            adresse = this.adresse,
            latitude = this.latitude.toFloat(),
            longitude = this.longitude.toFloat(),
            horaires = this.horaires,
            jours_ouvert = this.liste_jours_ouvert_dto.map { it.toDomain() },
            image_url = this.image_url,
            email = this.email,
            telephone = this.telephone,
            ispmr = this.ispmr,
            zone = this.zone,
            paiements = this.paiements,
            acces = this.acces,
            ouvert = this.ouvert,
            actif = this.actif,
            // ... autres champs ...
            estFavori = estFavori
        )

    fun ListeRestaurantsDto.toDomain(): ListeRestaurants =
        ListeRestaurants(
            restaurants = this.liste_restaurants_dto.map { it.toDomain() }
        )
}