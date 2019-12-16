/**** OpenClassrooms - Projet 7 ****/
/**** Développez le nouveau système d’information de la bibliothèque d’une grande ville ****/


INSERT INTO public.compte
    (nom, prenom, adresse, code_postal, ville, num_portable, num_domicile, num_carte_bibliotheque, mail, mot_de_passe)
VALUES 
    ('Carre', 'Evan', '65 Rue Basfroi', 79170, 'Tilly', 0646864294, null, 656310049, 'evancarre@fakeemail.tld', 'Evan'),
    ('Jolivet', 'Gilbert', '82 Cité Hiver', 77184, 'Tilly', 0686081870, null, 821157968, 'gilbertjolivet@fakeemail.tld', 'Gilbert'),
    ('Le gall', 'Nolan', '18 Rue Esquirol', 36310, 'Tilly', null, 0460941524, 189275124, 'nolanle.gall@fakeemail.tld', 'Nolan')
;


INSERT INTO public.ouvrage
    (titre, genre, resumer, auteur, editeur, ref)
VALUES 
    ('Dune - Tome 1 : Dune', 'science-fiction', 'Il n''y a pas, dans tout l''Empire, de planète plus inhospitalière que Dune. Partout des sables à perte de vue. Une seule richesse : l''épice de longue vie, née du désert, et que tout l''univers convoite. Quand Leto Atréides reçoit Dune en fief, il flaire le piège. Il aura besoin des guerriers Fremen qui, réfugiés au fond du désert, se sont adaptés à une vie très dure en préservant leur liberté, leurs coutumes et leur foi. Ils rêvent du prophète qui proclamera la guerre sainte et changera le cours de l''histoire. Cependant les Révérendes Mères du Bene Gesserit poursuivent leur programme millénaire de sélection génétique ; elles veulent créer un homme qui concrétisera tous les dons latents de l''espèce. Le Messie des Fremen est-il déjà né dans l''Empire ?', 'Frank Patrick Herbert', 'Pocket', '978-2266233200'),
    
    ('Fondation - Le cycle de Fondation Tome 1', 'science-fiction', 'En ce début de treizième millénaire, l''Empire n''a jamais été aussi puissant, aussi étendu à travers toute la galaxie. C''est dans sa capitale, Trantor, que l''éminent savant Hari Seldon invente la psychohistoire, une science nouvelle permettant de prédire l''avenir. Grâce à elle, Seldon prévoit l''effondrement de l''Empire d''ici cinq siècles, suivi d''une ère de ténèbres de trente mille ans. Réduire cette période à mille ans est peut-être possible, à condition de mener à terme son projet : la Fondation, chargée de rassembler toutes les connaissances humaines. Une entreprise visionnaire qui rencontre de nombreux et puissants détracteurs...', 'Isaac Asimov', 'Gallimard', '978-2070360536'),
    
    ('L''Assassin royal - Tome 1 : L''apprenti assassin', 'Fantasy', 'Au royaume des six Duchés, le prince Chevalerie, de la famille régnante des Loinvoyant - par tradition, le nom des seigneurs doit modeler leur caractère- décide de renoncer à son ambition de devenir roi-servant en apprenant l''existence de Fitz, son fils illégitime. Le jeune bâtard grandit à Castelcerf, sous l''égide du maître d''écurie Burrich. Mais le roi Subtil impose bientôt que FITZ reçoive, malgré sa condition, une éducation princière. L''enfant découvrira vite que le véritable dessein du monarque est autre : faire de lui un assassin royal. Et tandis que les attaques des pirates rouges mettent en péril la contrée, Fitz va constater à chaque instant que sa vie ne tient qu''à un fil : celui de sa lame...', 'Robin Hobb', 'J''ai Lu', '978-2290352625'),
    
    ('Chronique du tueur de roi - Première journée Tome 1 : Le Nom du vent', 'Fantasy', 'Un homme prêt à mourir raconte sa propre vie, celle du plus grand magicien de tous les temps. Son enfance dans une troupe de comédiens ambulants, ses années de misère dans une ville rongée par le crime, avant son entrée, à force de courage et d''audace, dans une prestigieuse école de magie où l''attendent de terribles dangers et de fabuleux secrets... Découvrez l''extraordinaire destin de Kvothe : magicien de génie, voleur accompli, musicien d''exception... infâme assassin. Découvrez la vérité qui a créé la légende.', 'Patrick Rothfuss', 'Bragelonne', '978-2352943556')
;

INSERT INTO public.livre
    (ref_bibliotheque, ouvrage_id, statut)
VALUES
    ('TISFFPHDT1N001', 1, 'indisponible'),
    ('TISFFPHDT1N002', 1, 'indisponible'),
    ('TISFFPHDT1N003', 1, 'indisponible'),
    
    ('TISFIAFLCDFT1N001', 2, 'indisponible'),
    ('TISFIAFLCDFT1N002', 2, 'disponible'),
    ('TISFIAFLCDFT1N003', 2, 'disponible'),
    
    ('TIFRHARAAT1N001', 3, 'indisponible'),
    ('TIFRHARAAT1N002', 3, 'disponible'),
    ('TIFRHARAAT1N003', 3, 'disponible'),
    
    ('TIFPRCDTDRLNVT1N001', 4, 'disponible'),
    ('TIFPRCDTDRLNVT1N002', 4, 'disponible'),
    ('TIFPRCDTDRLNVT1N003', 4, 'disponible')
;

INSERT INTO public.photo
    (url_photo, nom_photo, ouvrage_id)
VALUES
    ('Dune_1.jpg', 'Dune_tome_1', 1),
    ('Fondation_1.jpg', 'Fondation_tome_1', 2),
    ('Assassin_royal_1.jpg', 'Assassin_royal_tome_1', 3),
    ('Le_nom_du_vent_1.jpg', 'Le_nom_du_vent_tome_1', 4)
;    
    
INSERT INTO public.emprunt
    (date_debut, date_fin, prolongation, livre_id, compte_id)
VALUES
    ('2019-12-20', '2020-01-20', false, 1, 3),
    ('2019-11-30', '2019-12-30', false, 2, 1),
    ('2019-11-02', '2019-12-02', false, 4, 1),
    ('2019-12-05', '2020-01-05', false, 3, 2),
;
    
INSERT INTO public.reservation
	(date_demande_de_resa, num_position_resa, ouvrage_id, compte_id)
VALUES
    ('2019-12-04', 1, 1, 1),
    ('2019-12-04', 1, 2, 2);

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    