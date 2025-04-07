PGDMP      5                }         
   BooklandDB    17.2    17.2 U    7           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            8           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            9           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            :           1262    16386 
   BooklandDB    DATABASE     �   CREATE DATABASE "BooklandDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "BooklandDB";
                     postgres    false            �            1255    32823    authorized_delete()    FUNCTION     �   CREATE FUNCTION public.authorized_delete() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
    DELETE FROM authorized WHERE date_authorization < NOW() - INTERVAL '3 days';
    RETURN NULL;
END;$$;
 *   DROP FUNCTION public.authorized_delete();
       public               postgres    false            �            1259    16387    articles    TABLE     �  CREATE TABLE public.articles (
    id integer NOT NULL,
    description text,
    publication timestamp(6) without time zone NOT NULL,
    text text,
    title character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    author_id integer NOT NULL,
    CONSTRAINT articles_type_check CHECK (((type)::text = ANY ((ARRAY['BOOKS'::character varying, 'SHOP'::character varying, 'OTHER'::character varying])::text[])))
);
    DROP TABLE public.articles;
       public         heap r       postgres    false            �            1259    16405    articles_seq    SEQUENCE     v   CREATE SEQUENCE public.articles_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.articles_seq;
       public               postgres    false            �            1259    24638 
   authorized    TABLE     �   CREATE TABLE public.authorized (
    id integer NOT NULL,
    date_authorization timestamp(6) without time zone NOT NULL,
    refresh_token character varying(255) NOT NULL,
    user_id integer NOT NULL
);
    DROP TABLE public.authorized;
       public         heap r       postgres    false            �            1259    24643    authorized_seq    SEQUENCE     x   CREATE SEQUENCE public.authorized_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.authorized_seq;
       public               postgres    false            �            1259    41022    authors    TABLE     �   CREATE TABLE public.authors (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    second_name character varying(255),
    last_name character varying(255) NOT NULL
);
    DROP TABLE public.authors;
       public         heap r       postgres    false            �            1259    41049    authors_seq    SEQUENCE     u   CREATE SEQUENCE public.authors_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.authors_seq;
       public               postgres    false            �            1259    49393    book_images    TABLE     �   CREATE TABLE public.book_images (
    id integer NOT NULL,
    location character varying(255) NOT NULL,
    book_id integer
);
    DROP TABLE public.book_images;
       public         heap r       postgres    false            �            1259    49403    book_images_seq    SEQUENCE     y   CREATE SEQUENCE public.book_images_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.book_images_seq;
       public               postgres    false            �            1259    41058    books    TABLE     q  CREATE TABLE public.books (
    isbn integer NOT NULL,
    about text NOT NULL,
    name character varying(255) NOT NULL,
    pages integer NOT NULL,
    prise integer NOT NULL,
    quantity integer NOT NULL,
    genre_id integer NOT NULL,
    publisher_id integer NOT NULL,
    series_id integer NOT NULL,
    type_id integer NOT NULL,
    release integer NOT NULL
);
    DROP TABLE public.books;
       public         heap r       postgres    false            �            1259    49219    books_authors    TABLE     f   CREATE TABLE public.books_authors (
    book_isbn integer NOT NULL,
    author_id integer NOT NULL
);
 !   DROP TABLE public.books_authors;
       public         heap r       postgres    false            �            1259    41070 	   books_seq    SEQUENCE     s   CREATE SEQUENCE public.books_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.books_seq;
       public               postgres    false            �            1259    32830    comments    TABLE     �   CREATE TABLE public.comments (
    id integer NOT NULL,
    date timestamp(6) without time zone NOT NULL,
    text character varying(255) NOT NULL,
    article_id integer NOT NULL,
    author_id integer NOT NULL
);
    DROP TABLE public.comments;
       public         heap r       postgres    false            �            1259    32835    comments_seq    SEQUENCE     v   CREATE SEQUENCE public.comments_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.comments_seq;
       public               postgres    false            �            1259    41029    genres    TABLE     b   CREATE TABLE public.genres (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public.genres;
       public         heap r       postgres    false            �            1259    41050 
   genres_seq    SEQUENCE     t   CREATE SEQUENCE public.genres_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.genres_seq;
       public               postgres    false            �            1259    16412 	   materials    TABLE     �   CREATE TABLE public.materials (
    id integer NOT NULL,
    location character varying(255) NOT NULL,
    text character varying(255),
    article_id integer NOT NULL
);
    DROP TABLE public.materials;
       public         heap r       postgres    false            �            1259    16426    materials_seq    SEQUENCE     w   CREATE SEQUENCE public.materials_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.materials_seq;
       public               postgres    false            �            1259    41034 
   publishers    TABLE     f   CREATE TABLE public.publishers (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public.publishers;
       public         heap r       postgres    false            �            1259    41051    publishers_seq    SEQUENCE     x   CREATE SEQUENCE public.publishers_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.publishers_seq;
       public               postgres    false            �            1259    41039    series    TABLE     �   CREATE TABLE public.series (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    publisher_id integer NOT NULL
);
    DROP TABLE public.series;
       public         heap r       postgres    false            �            1259    41052 
   series_seq    SEQUENCE     t   CREATE SEQUENCE public.series_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.series_seq;
       public               postgres    false            �            1259    16438    test_seq    SEQUENCE     r   CREATE SEQUENCE public.test_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.test_seq;
       public               postgres    false            �            1259    41044    types    TABLE     a   CREATE TABLE public.types (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public.types;
       public         heap r       postgres    false            �            1259    16427 	   types_seq    SEQUENCE     s   CREATE SEQUENCE public.types_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.types_seq;
       public               postgres    false            �            1259    16395    users    TABLE     �  CREATE TABLE public.users (
    id integer NOT NULL,
    icon character varying(255),
    login character varying(255) NOT NULL,
    name character varying(255),
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying])::text[])))
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    16406 	   users_seq    SEQUENCE     s   CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.users_seq;
       public               postgres    false                      0    16387    articles 
   TABLE DATA           ^   COPY public.articles (id, description, publication, text, title, type, author_id) FROM stdin;
    public               postgres    false    217    c       #          0    24638 
   authorized 
   TABLE DATA           T   COPY public.authorized (id, date_authorization, refresh_token, user_id) FROM stdin;
    public               postgres    false    225   Td       '          0    41022    authors 
   TABLE DATA           C   COPY public.authors (id, name, second_name, last_name) FROM stdin;
    public               postgres    false    229   e       3          0    49393    book_images 
   TABLE DATA           <   COPY public.book_images (id, location, book_id) FROM stdin;
    public               postgres    false    241   nf       0          0    41058    books 
   TABLE DATA              COPY public.books (isbn, about, name, pages, prise, quantity, genre_id, publisher_id, series_id, type_id, release) FROM stdin;
    public               postgres    false    238   �h       2          0    49219    books_authors 
   TABLE DATA           =   COPY public.books_authors (book_isbn, author_id) FROM stdin;
    public               postgres    false    240   N�       %          0    32830    comments 
   TABLE DATA           I   COPY public.comments (id, date, text, article_id, author_id) FROM stdin;
    public               postgres    false    227   ��       (          0    41029    genres 
   TABLE DATA           *   COPY public.genres (id, name) FROM stdin;
    public               postgres    false    230   �                 0    16412 	   materials 
   TABLE DATA           C   COPY public.materials (id, location, text, article_id) FROM stdin;
    public               postgres    false    221   �       )          0    41034 
   publishers 
   TABLE DATA           .   COPY public.publishers (id, name) FROM stdin;
    public               postgres    false    231   ��       *          0    41039    series 
   TABLE DATA           8   COPY public.series (id, name, publisher_id) FROM stdin;
    public               postgres    false    232   �       +          0    41044    types 
   TABLE DATA           )   COPY public.types (id, name) FROM stdin;
    public               postgres    false    233   ��                 0    16395    users 
   TABLE DATA           F   COPY public.users (id, icon, login, name, password, role) FROM stdin;
    public               postgres    false    218   :�       ;           0    0    articles_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.articles_seq', 451, true);
          public               postgres    false    219            <           0    0    authorized_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.authorized_seq', 351, true);
          public               postgres    false    226            =           0    0    authors_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.authors_seq', 1, false);
          public               postgres    false    234            >           0    0    book_images_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.book_images_seq', 1, false);
          public               postgres    false    242            ?           0    0 	   books_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.books_seq', 1, false);
          public               postgres    false    239            @           0    0    comments_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.comments_seq', 101, true);
          public               postgres    false    228            A           0    0 
   genres_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.genres_seq', 1, false);
          public               postgres    false    235            B           0    0    materials_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.materials_seq', 501, true);
          public               postgres    false    222            C           0    0    publishers_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.publishers_seq', 1, false);
          public               postgres    false    236            D           0    0 
   series_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.series_seq', 1, false);
          public               postgres    false    237            E           0    0    test_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.test_seq', 1, false);
          public               postgres    false    224            F           0    0 	   types_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.types_seq', 1, false);
          public               postgres    false    223            G           0    0 	   users_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.users_seq', 51, true);
          public               postgres    false    220            a           2606    16394    articles articles_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.articles
    ADD CONSTRAINT articles_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.articles DROP CONSTRAINT articles_pkey;
       public                 postgres    false    217            i           2606    24642    authorized authorized_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.authorized
    ADD CONSTRAINT authorized_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.authorized DROP CONSTRAINT authorized_pkey;
       public                 postgres    false    225            m           2606    41028    authors authors_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.authors DROP CONSTRAINT authors_pkey;
       public                 postgres    false    229            {           2606    49397    book_images book_images_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.book_images
    ADD CONSTRAINT book_images_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.book_images DROP CONSTRAINT book_images_pkey;
       public                 postgres    false    241            y           2606    49223     books_authors books_authors_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.books_authors
    ADD CONSTRAINT books_authors_pkey PRIMARY KEY (book_isbn, author_id);
 J   ALTER TABLE ONLY public.books_authors DROP CONSTRAINT books_authors_pkey;
       public                 postgres    false    240    240            w           2606    41064    books books_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (isbn);
 :   ALTER TABLE ONLY public.books DROP CONSTRAINT books_pkey;
       public                 postgres    false    238            k           2606    32834    comments comments_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.comments DROP CONSTRAINT comments_pkey;
       public                 postgres    false    227            o           2606    41033    genres genres_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.genres
    ADD CONSTRAINT genres_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.genres DROP CONSTRAINT genres_pkey;
       public                 postgres    false    230            g           2606    16418    materials materials_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.materials
    ADD CONSTRAINT materials_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.materials DROP CONSTRAINT materials_pkey;
       public                 postgres    false    221            q           2606    41038    publishers publishers_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.publishers
    ADD CONSTRAINT publishers_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.publishers DROP CONSTRAINT publishers_pkey;
       public                 postgres    false    231            s           2606    41043    series series_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.series
    ADD CONSTRAINT series_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.series DROP CONSTRAINT series_pkey;
       public                 postgres    false    232            u           2606    41048    types types_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.types
    ADD CONSTRAINT types_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.types DROP CONSTRAINT types_pkey;
       public                 postgres    false    233            c           2606    16404 !   users ukow0gan20590jrb00upg3va2fn 
   CONSTRAINT     ]   ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukow0gan20590jrb00upg3va2fn UNIQUE (login);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT ukow0gan20590jrb00upg3va2fn;
       public                 postgres    false    218            e           2606    16402    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    218            �           2620    32827    authorized delete_auth_trigger    TRIGGER     �   CREATE TRIGGER delete_auth_trigger AFTER INSERT OR UPDATE ON public.authorized FOR EACH STATEMENT EXECUTE FUNCTION public.authorized_delete();
 7   DROP TRIGGER delete_auth_trigger ON public.authorized;
       public               postgres    false    225    243            ~           2606    24644 &   authorized fk163ncd4v3g0ntfnkxv3e9fsqq    FK CONSTRAINT     �   ALTER TABLE ONLY public.authorized
    ADD CONSTRAINT fk163ncd4v3g0ntfnkxv3e9fsqq FOREIGN KEY (user_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.authorized DROP CONSTRAINT fk163ncd4v3g0ntfnkxv3e9fsqq;
       public               postgres    false    225    218    4709            �           2606    49224 )   books_authors fk3qua08pjd1ca1fe2x5cgohuu5    FK CONSTRAINT     �   ALTER TABLE ONLY public.books_authors
    ADD CONSTRAINT fk3qua08pjd1ca1fe2x5cgohuu5 FOREIGN KEY (author_id) REFERENCES public.authors(id);
 S   ALTER TABLE ONLY public.books_authors DROP CONSTRAINT fk3qua08pjd1ca1fe2x5cgohuu5;
       public               postgres    false    229    240    4717            �           2606    41086 !   books fk5efosbu78pe11me1akmnpr05a    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fk5efosbu78pe11me1akmnpr05a FOREIGN KEY (type_id) REFERENCES public.types(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fk5efosbu78pe11me1akmnpr05a;
       public               postgres    false    4725    238    233            �           2606    41071 !   books fk9hsvoalyniowgt8fbufidqj3x    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fk9hsvoalyniowgt8fbufidqj3x FOREIGN KEY (genre_id) REFERENCES public.genres(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fk9hsvoalyniowgt8fbufidqj3x;
       public               postgres    false    238    230    4719            �           2606    41053 "   series fk9x6a45t5w6oy35y6hy8bnk711    FK CONSTRAINT     �   ALTER TABLE ONLY public.series
    ADD CONSTRAINT fk9x6a45t5w6oy35y6hy8bnk711 FOREIGN KEY (publisher_id) REFERENCES public.publishers(id);
 L   ALTER TABLE ONLY public.series DROP CONSTRAINT fk9x6a45t5w6oy35y6hy8bnk711;
       public               postgres    false    4721    232    231            �           2606    41076 !   books fkayy5edfrqnegqj3882nce6qo8    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fkayy5edfrqnegqj3882nce6qo8 FOREIGN KEY (publisher_id) REFERENCES public.publishers(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fkayy5edfrqnegqj3882nce6qo8;
       public               postgres    false    231    238    4721            �           2606    49404 '   book_images fkcnpy06tjmrsjisjf2bqpuvvbl    FK CONSTRAINT     �   ALTER TABLE ONLY public.book_images
    ADD CONSTRAINT fkcnpy06tjmrsjisjf2bqpuvvbl FOREIGN KEY (book_id) REFERENCES public.books(isbn);
 Q   ALTER TABLE ONLY public.book_images DROP CONSTRAINT fkcnpy06tjmrsjisjf2bqpuvvbl;
       public               postgres    false    4727    238    241            |           2606    16407 $   articles fke02fs2ut6qqoabfhj325wcjul    FK CONSTRAINT     �   ALTER TABLE ONLY public.articles
    ADD CONSTRAINT fke02fs2ut6qqoabfhj325wcjul FOREIGN KEY (author_id) REFERENCES public.users(id);
 N   ALTER TABLE ONLY public.articles DROP CONSTRAINT fke02fs2ut6qqoabfhj325wcjul;
       public               postgres    false    4709    218    217            �           2606    49229 )   books_authors fkg92282id84r9l5youm7y12vpg    FK CONSTRAINT     �   ALTER TABLE ONLY public.books_authors
    ADD CONSTRAINT fkg92282id84r9l5youm7y12vpg FOREIGN KEY (book_isbn) REFERENCES public.books(isbn);
 S   ALTER TABLE ONLY public.books_authors DROP CONSTRAINT fkg92282id84r9l5youm7y12vpg;
       public               postgres    false    240    238    4727            �           2606    41081 !   books fkh16ssynmso8qdbwd7jtkx2ifg    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fkh16ssynmso8qdbwd7jtkx2ifg FOREIGN KEY (series_id) REFERENCES public.series(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fkh16ssynmso8qdbwd7jtkx2ifg;
       public               postgres    false    4723    232    238            }           2606    16433 %   materials fkiu3bn87ci0k6ji4t2qh0b828d    FK CONSTRAINT     �   ALTER TABLE ONLY public.materials
    ADD CONSTRAINT fkiu3bn87ci0k6ji4t2qh0b828d FOREIGN KEY (article_id) REFERENCES public.articles(id);
 O   ALTER TABLE ONLY public.materials DROP CONSTRAINT fkiu3bn87ci0k6ji4t2qh0b828d;
       public               postgres    false    4705    221    217                       2606    32836 $   comments fkk4ib6syde10dalk7r7xdl0m5p    FK CONSTRAINT     �   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkk4ib6syde10dalk7r7xdl0m5p FOREIGN KEY (article_id) REFERENCES public.articles(id);
 N   ALTER TABLE ONLY public.comments DROP CONSTRAINT fkk4ib6syde10dalk7r7xdl0m5p;
       public               postgres    false    217    227    4705            �           2606    32841 $   comments fkn2na60ukhs76ibtpt9burkm27    FK CONSTRAINT     �   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkn2na60ukhs76ibtpt9burkm27 FOREIGN KEY (author_id) REFERENCES public.users(id);
 N   ALTER TABLE ONLY public.comments DROP CONSTRAINT fkn2na60ukhs76ibtpt9burkm27;
       public               postgres    false    227    4709    218               $  x���=k�0�g�W�첾e٨�
�RڎYd}X)JlbA鿯ݦ4%KK���{�祘�Ϲ��
�$��ŬŴ�:�c��'��7��裭{?��u z��M_�>0���&�<��њ�9�
+��4�h�?LB��FԢ�D	���^�0`���A�>T/�6�cE�q1Q�	"���A�Y��$SԚ[���
���@�|��,�j��ք3�m~24��]T��?��=o�RRA�i�߉Q@D�p�U��:��S��xN�x��R����\�O����ʾ*���>��      #   �   x����   г~E? C@So��AC�e��gjm��g���!�D����q#���bkX���)gr�Hy�\���3o/�S�fE������b�P1݉�L�_��q�t.��7�[J$��a_�A�Vr���W_�U���ߨOfڅAn��ض�6�0�      '   Z  x�]Q[R�@��9�傊^�p���� ��(�D��⑐\��F��Z�Ojg�����~B�*�9�0���;
l�����m�M�G�0���&�(Bb366��ȹ�'Z���C�����Z.>�3򔷑ca��anMr�t�)g3�Y��� ��hM�$WO!	=�=�:$ ��Daɪ���L�a�Sv+�FCc�h �e}N-u��k����À��JF�5���L|Gu��#��P1-�F���u��*ɴ�兜1T��x9�<�o�IG�]1%rbe�1*������VO�|��������݁�S��O�ьvm��� ��t� �p�w��-�{""�L
z�      3   w  x�U�;-)���^� *�e�'��}N�W�ѯ]T�$�Ea� g/�� �4[����c����I�:� L�<��0w �w"��R��V=��ȏ�ϥD�~h�QRa�TX��l!N�V՗�vӋ�X,uӂ�����Fݍx�']K����������:�E���
�g��=�� ���}�[���S	m�m����sXy���$/���*��.���F2��՟t+�����u���������ḙ*���N����΅�Ѹ?�^��� �� �|V��v\rȥ)�L��W��M�{�QH�pƼ�׀5��y��QH��#�sZ� sT�9j�N3���)sݮk�g�튆bl-N�+�Hʘ�N[�Hg���՗�a͡��6�a������4�sTU��̪ى��f�e�B��x��r�}��ۛ�G�l���J+��� HJYiB�n>������
6���a�ѥ2����w�1_|z��9��\���Ua����O�3-5Z��Ƀ^�χ.kG=@c���\��9p;[�zC��Z�}�v=3��
e�J3�)����Փk��?�0ɒ��z���G��{v��9�8�V.�\����VX���/Κ���r��Zq����-���zK�      0      x��[[n�X���VA�k�Kֳ����ZC�!��-y$K~MO���ˮ��P̤��b^��ZIG�qy�JW{00 K�$��<q"��4�q�l򴩚Iڞ4y�h/ڧi3�_��n���|[4y{�>o*��J�US�g�ݼ�l���jO�S�R5�T��jn�>����״Y���֧6��N嗲�5��j~�nm�S\����θ)�g���Gslo.OX�?�R�,�gr�}���'�����_R˚��)��O�/X��J὜R�/{�I�X����md��Y�>��o��g��'A4�7{&�_���Α�Xo�屷�y�O��2n\6�Xdu��<h�m��6x#���U�XE�A"���)SyؒZ�p�\�˗�f~i��I�I���'�mgi{�Jeo)E�[���������Nv������d���d�(ّ����ݽ����?��8�U{�6o�ײ�|����Ү���hgk�Ȣ�27O?Nu���%�3��D�0:ѕ~��n7o�-Mo��5dQ��Y�I����LSq�h[�VBf�Qt��0R<b�c�E�9|���>1�U�����s�Oh\j i�-S~���x�nMn�ëBx�R=[�K�R��:��o%����2���+U�j�+�����S�nQ{�^a�z�X�����=Vv	#?�&��nt��P�I��h�����`R�A9Ƶ*��x�)�0tߪ�O�4��4������֮�{�`��[�����75'0�� |���k��T����,�0�T��QO���>Nŭ�:@�e���o=J��s(����J6x�����*1hnL�L�96'�!��g�NOQ �O5�Ŋ�UԦ��!��;���x��"EFP�Ю�t�x��}G��u���eǶ���y�͕m�y���^���67í��go}Y�`�[]R�y�0(C��N1]k�anF�T���<uL'F2���%����!�.�.�\R�556�
�J7S��E2F���Q��g�)"c���*E�z��R��܏3! '�.~L�g�d~��ݸ��U��Ծ#/O�q+����J��ӥ'����FY�s�H�������:U�c}錇[1a�6����-�����ňa墙�ᨚ�+�,I�$ŮF0�1�v$v�R/I����Rs-���K2�b��y\�R�\p�h(y���<Z�iɂ{�^j#w�W�-!^B���kJ�H�����R��GT��,�" �!*�Ю�b��\�����Q>��$���M#F"���"�|%��b+3$]����B����&Q�Z���H���L��jD�K�NW�4��sWE{P\�|A�2��+�+�'/ޛ�5�k�d�,��>Z�����w����u���� 8`<�x�-@�@�8n���>G���,ab'���E�)�$��R�T-�~��P/��f"f�8�� ���g��>0
WM�ifL�&�K� ,�nq��&OhQ�3�6����>��)�s"�R��Cj��8�(�@�	<�ṃ��s�݀�j�
�M`w�	��qr��DV<���:��TMBu�{o/,�U�z&.���B�<$K<Ϣ+3dH<��C �Pb%Wn'7�z��f�Ʈ��%�|m�*����KH���D��c��)cƙ?Da(���M�'�������4�,?t8n�@��(�����&6�0�"EX�8�p`No���q��K�Q=�1HNLk�e#�xd1�Iw���sX�Q��gO�uH���e���Х�p	��51�����(��_#獙��`�3w��^+j�pw��pjTY8��N�,�P�H `7x����Pl��Z�� �KVT1S�Kϭ�ч�Ri*�޴����^r(�ٳ�ݭ-?_#*;G@������Vo��PF���G��Ց�4��C�mpV��	`��_QbN��!��_��#�����e^�+r(ߕ�D��Í�xd/��p�fuϽ�Лc1�`��E��;��Q��'P
�0�R�;~�F�C��� � h-`t�
�Ճ2/�F!`D��!�S�c$�!?�v���g�~d���g��VdE���b���q�g�fT8'��N�˫�Mj�H4יe�~�0���4�@�:�C*���VJ�R��@�Zi��{Σ���A)Mi-rb�vL6A���U�	b��(
��#����''��C��	1	�<"��5\乹���-z6g�\����fO�� �U�g
֥���y�Q�c|i�*�@d­k�>��;qG�S8�2��&���B�Z+�$�&x%����I�Oe�j�<���Z�(5Cd���ug���2�(�)˽R�����m�����ۯs���2	�6)[��Ϛ<�;z�>���&���C�s �t�b}f�����b�<'����z��+J�ri(���%	�,B�`�����e�JC�K=[�p���r�s�
fӕ	�J��\S@[��������[v��9R�a�(�PyT��t4����`i5����E'�I找�I�8�Z��A	l�Y��[f�^V�DE���Z�K�����#��<��_�r-5�|L����ӱ�B^�R�{�$Xb�?�n;��:��E&����4J��,�&���QM-׮���2$|�S�S꿟z�S~5)�AÉ��RJr0d���c��2�P��{�4���`�eh�C_KM�6�U=��&)駒��P{#EY��^�5��'�2��%��`O�ҮD$+���:Ҹ��㒬`{���\C�.�����yt���7ɼ�d�g�v�2�#?�q�Aș�y��[dklE�3�B�cX\��2�Ù9Rs�q@bI�"ձj���$$�����n��ƫ
4�o(j�|jER�[)�x�Q������(H��@�b!kF;"c\w����1�\q��)$�i�XY�Y�-eq��p�+S�:Z8���wk]��M(8�XN��bex_X�<eżb�d3���h��y��?���N�,,`[�&
�h���!�^9l�A�,N=�;��l6���l�Ȭ�@��dW�7��bK�{��ƨ��XY����&5����	�#�G�pcI
�z��ޔIw��;R<�*k��("B)�ϓ�%oe5[e�xQ+J���z����#�RR���׋Y���Ar�h_�@�}����mg�8i^��g޵�+��܍���%�]4�K�i�w̓z��~OG�.�)~b�9�`{�П�1�r_��V��~0"R˔�o�*�*��{��܋f�lua6�Q{��~�<Z���Ț.̓�<�k��!1q3�?4��'�V�V��ߑJ�8U�ŧMT]���X���y�Gp��5�k�V��??@&Zi2c�`�=��jۥ�x@u�n6i� ��X|Rea:���?��Dll�W�&��:GS��^����U,u"2X���C�so�=��ņ�2�Ŝ��Ъ9�'�Z����=�`��w��
�ۮ9�3}��)�^j�yS��,�*�}	3>AN�r��+4����Z�������N��4z-+�1�+t@��X��n�¿<	c%������c������Ѐ�D���!z���6 IV�\�hX-�
�q2_����s�D��;z��Q�G�ԧ�ڡ`r���k)"�@��jZцL\k|�,��C��D�1�V�"���������5J4jP��q
/�<[���x��� 6%���v$ksN���a����;"�Qw��J�1���+2�9M�������j���hv�_Zo���� p��%��T��C͍Aԉ��u֝�q����ȣ��W��L}�A���jj�=�na��,V�/�b���`���i"�"\�̄%��mP<e��%�,j�R��=�eh�l'��G�Ѿ 0A{��%��ց�l}���<��E��7�<�j�#�����"��~���AE��ĵ�$�Lc���0^��i���F wQ��h\�c��檀+!-����L�d'�A�3"���G���w	�as1��u qj+�du��E�C��e�G��{@��%��QW��5�J�Z�]�~�=YN68)C�M���: D  �^��)�86�%��Ð���qr��]�vv"����Vΰ$�������)��Dq�`'֢7����r@a��Kݐ�¦ر�Hr���Fe�"p��X���[��(�	���}��l�wRNa���g\��S�B%:���ô�OP�]��Zқp���Pv�����kT����E�w$�F.<У��֯��s�5 �z�v�;7,��q����[�51Ff��l�4gPd��T_pċQ��4�C�a�8�L���S��8se�ߍ�����h�E'O�]�k�o��/Xi^ԅ�l,(ղ2����6W��P�ʶ�˾����+�n߮�׵����{�i&��g,�9����D��x�����r�qƚ��Nб�a�e�Pπ{��>��&�co_�o4�-��&������M�)�m{��dfl�o���,B�@?D�j���i_�W-�.l�S��'t��e��v�|*3=��]x���FuY�s�+wS�$�U
�* ��D�EK�6��w���?GMe�p�>]�����-�C�N�\�0�[��q�Z��8kkylb���3�v�z8ݽ$ACj�9�MNa$%�bgIBm�#�-�����A��@�f�~�u�-Č8�9�9�#hU��A�����c� ��	b�P��+�?z�KR>+�B�nNh����@�P��,'&;j Z������{s��8yA�.9?e��Mկܸ޼�^�}�[t���к-Kô�i�eͱ
��hR�2's,�n4:�����;�7��y!veӬq��6N� �b�q�u936ȺK՟�9�CG2*�}2x��8�1 ��^XD�j�ĉU�cf��N����$� �� goZM-���!?�:����C�>b��\�DYW��q�QL��)�������ݪ�i�9��vFR��&Pc>� ��J��܍]��΁@J�O�(��'�G������l��Lp�B� 
��TF�!�4'J=aU�2^����F#>�g|iQ�^�s��0����Ô!"5A��B䨼��4��8:�ܩ�}�aT
*�9>���N��G�p��:Ӟ��u�A:"j��ٻMF��������h#���J@�����Z�%���ѮhW��qW<xU��4��SMc_�ɋ�Ey�+13J�)Y�������dz7�:��x�9�fX�v�]�]�QКC0<��i�����pE<Pw�.E>I\𿘈�ރ�a��2�~���FOU��'Ҝ��ϊ����O)UjXc+|t�W���1�D����_�z>�Z��U�1��������>"_[���~D�����hm�'���6)hL���Gv�~D��W%��]Pp�ݫA#����l��� $���}�106��b�W$�^�Ѭv���=r&:'��F������@OjQ�<�t�s��Vvqj�
/�L�g�B���0,�,tQkϴ^؎
��Ɨw،�@��&����f� ��e�0[SFI��i�&�� �u���(�����!\3H���p�B�(�^x�w��6��"��Waj�k�W��b��o}�L��o}����\��0�&�c��Zd��"zo��]��˻�����]�O6#���W���7QmN��e�`?����9[�q���}h~�re/[irn�:NH�����l^z�PMw��������:Ѓd�Q񟄺�Dg��r1ǵ'�l=H| �<ⶲ�z3��/��j�k�������c�����fa���u�7��L�ԓ��Y�����-x��>� ���(!T��f���ތE����������eȀj"�Q��*]���1��X}um����m�I6���uzNU�#���=/��Ƹʐh}p�R��2�t��t\/��ˍC�T,��Y�_i(�t]�pa�4P�����$��2��W��׍�����y��
�uo�X�[Vh���%a��#��|�]s礻�W̅uՠ��%�Y?��� �J<a����|�u�i�1�	���%rD#�g�r��ʮ�[�]����	� �OT���}k߅�rWS�?=�.�^��h.�3�y-Ø����±b�T��j�H�ٞ淋��w����3�&����AԤ���a{kk�L�qW      2   ?   x�˱�0��?L`ʊ����\\A2{�G�E��#7�6^g�J�[�S�'Yx��~�]
      %   X   x�U�1� F�=����gq�82���$&���Os�bA̵9rRS:{��$a�O5�ً���q\�UB	R]�i�J�N�%f�<�      (     x�]�MN�@���S� ���K�I�X�B�Xu��%TQӄ+�7��DlP�����{1�:��r�͖:X�-�NNih��%�Nw�΂�B��k��J΃��HY�)A����.Ӿ�\���ҥ�A�� �u������{��[�CF�㠟@\5Ơk����J"�Wl-���N"�7�߸����������Vi;c<LM�Dx�f��t�h�������Yr�
\��dA��l3���)y?0��=�����<;���u��#�a��H         Y   x�M�9�  ������0�3V���u�eE1��M8�a�nȮ��ikg{޸K}T*�]P@#�i�Q8���<�Ƶ~�l�����      )   �   x�3�0������8/����b�=�q�D�_�x��®�L8/L��(��¾�@S���=�_�qa/P���3.,�2��/H�/�Sp���.��
�jk���
d�L��
ur������ ��F      *   �  x�}RKNA\w���+��	p�	ܹb� FѰ]�%���q��
�nd�4�D3�t�}�ޫjo��K̤�X��`����1c��X�9r'}���H��l0�[f�R1�G"��$"̊_"]��N��uv��M�V�,�;�)r%�;<2�;b8CJ���s���f�5 ��2LR�s��a��\�i�����E����؆�c%7r����5wޘ�m�1��a_W3D��!Y����̓j�{#d���D+4��B��&��QT�0T�T��Oa��b��,�iZOէd��VT��_r�.��ӗ	�>���'`-z�M�Ҙ��X�<�j�nH�Tj��<|A�-�K	2���,;	���`���#�w���'�i�Z�	�פ_      +      x�mν�@���< �Ü���h���
.��z#� ѥ����U��w
=�:�t��=�:p�Uy�aNl'F�d���JߺP������B��I�j���A���OJ)n&�e=�m�4�/i�x�         �   x�-�Mo�0 �s�\���-�c�e
�Q�]hi4C���G�]��C�V�SVplp��� PX�m�V
K���e�ӡi���r�W}{h��[�%B���<�[T_�>
��R�5g2cI�e�6�I�1�����Ð�!�	�j0��(r�u`���ֈb|�uf|���K6k仉O�E�y�6��z������|��H�I�<*�6�v�vh��V�q�IRG�     