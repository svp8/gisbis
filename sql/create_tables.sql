CREATE TABLE IF NOT EXISTS entity (
    id serial NOT NULL,
    geom geometry(linestring, 3857) NOT NULL,
    color text NOT NULL,
    CONSTRAINT entity_pkey PRIMARY KEY(id)
);
drop index if exists entity_geom_index;

insert into entity (id, geom, color)
select
  id id,
  ST_MakeLine(p, ST_Translate(a.p, random()*2000000 - 0.0005, random()*1300000 - 0.0005)) geom,
  concat('#', lpad(to_hex((random()*x'FFFFFF'::int8)::int8), 6, '0')) color
from (
  select
    id,
    ST_Point(random()*200 + 29, random() + 59.5, 3857) p
  from generate_series(1, 200000, 1) as id
) a
on conflict (id)
do update set
  geom = EXCLUDED.geom,
  color = EXCLUDED.color;

create index if not exists entity_geom_index ON entity USING gist (geom);