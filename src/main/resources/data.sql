-- ============================================================
--   VIEWS: Consultation
-- ============================================================

-- Liste des joueurs de tous les dates.
create or replace view players_list as
SELECT pl.*, m.match_id, m.match_date, m.season_id
FROM players pl
    JOIN player_match_performance pr ON pl.person_id = pr.person_id
    JOIN matches m ON m.match_id = pr.match_id;

-- Table rassemblant les deux équipes participants aux rencontres
create or replace view teams_matches as
SELECT DISTINCT m.match_id as match_id, pr.team_id as team_id
FROM matches m JOIN player_match_performance pr ON pr.match_id = m.match_id;

-- Table de rencontres avec plus d'informations comme les numeros d'équipe par exemple
create or replace view extended_matches as
SELECT m.match_id as match_id, m.match_date as match_date, m.season_id as season_id, m.journee as journee, MAX(tm.team_id) as team_id1, MIN(tm.team_id) as team_id2
FROM matches m JOIN teams_matches tm ON m.match_id = tm.match_id
GROUP BY m.match_id, m.match_date, m.season_id, m.journee;

-- Feuille de match à tous les dates
create or replace view match_summary as
SELECT m.match_id, m.match_date as match_date, pe.person_id as person_id, pe.last_name, pe.first_name, pl.licence_number, pl.team_id, pr.score_accumulation, pr.faults_accumulation
FROM people pe, players pl, extended_matches m, player_match_performance pr
WHERE pe.person_id = pl.person_id AND pr.person_id = pe.person_id AND m.match_id = pr.match_id;

-- Feuille de match résumée à tous les dates
create or replace view match_summary_resume as
SELECT match_id, team_id, sum(score_accumulation) as team_score, sum(faults_accumulation) as team_faults
FROM match_summary
GROUP BY match_id, team_id;

-- Score de l'équipe 1 dans un match à tous les dates
create or replace view team_score1 as
SELECT m.match_id as match_id, m.team_id1, sum(pr.score_accumulation) as score, sum(pr.faults_accumulation) as faults
FROM extended_matches m, player_match_performance pr, players pl
WHERE pr.match_id = m.match_id AND pr.person_id = pl.person_id AND m.team_id1 = pr.team_id
GROUP BY m.match_id, m.team_id1;


-- Score de l'équipe 2 dans un match à tous les dates
create or replace view team_score2 as
SELECT m.match_id as match_id, m.team_id2, sum(pr.score_accumulation) as score, sum(pr.faults_accumulation) as faults
FROM extended_matches m, player_match_performance pr, players pl
WHERE pr.match_id = m.match_id AND pr.person_id = pl.person_id AND pr.team_id = m.team_id2
GROUP BY m.match_id, m.team_id2;


-- Score d'un mactch à tous les dates
create or replace view match_score as
SELECT m.match_id, m.match_date as match_date, m.team_id1, ts1.score as team_score1, ts2.score as team_score2, m.team_id2
FROM extended_matches m, team_score1 ts1, team_score2 ts2
WHERE m.match_id = ts1.match_id AND m.match_id = ts2.match_id;

-- counter of HOME wins
create or replace view count_home_wins as
SELECT t.team_id, COUNT(num_wins) as wins
FROM teams t
    FULL OUTER JOIN
    (
        SELECT ms.team_id1 as team_id, COUNT(*) as num_wins
        FROM match_score ms
        WHERE ms.team_score1 > ms.team_score2
        GROUP BY ms.team_id1
    ) T
ON t.team_id = T.team_id
GROUP BY t.team_id;

-- counter of HOME losses
create or replace view count_home_losses as
SELECT t.team_id, COUNT(num_losses) as LOSSES
FROM teams t
    FULL OUTER JOIN
    (
        SELECT ms.team_id1 as team_id, COUNT(*) as num_losses
        FROM match_score ms
        WHERE ms.team_score1 < ms.team_score2
        GROUP BY ms.team_id1
    ) T
ON t.team_id = T.team_id
GROUP BY t.team_id;

-- counter of HOME draws
create or replace view count_home_draws as
SELECT t.team_id, COUNT(num_draws) as draws
FROM teams t
    FULL OUTER JOIN
    (
        SELECT ms.team_id1 as team_id, COUNT(*) as num_draws
        FROM match_score ms
        WHERE ms.team_score1 = ms.team_score2
        GROUP BY ms.team_id1
    ) T
ON t.team_id = T.team_id
GROUP BY t.team_id;

-- counter of AWAY wins
create or replace view count_away_wins as
SELECT t.team_id, COUNT(num_wins) as wins
FROM teams t
    FULL OUTER JOIN
    (
        SELECT ms.team_id2 as team_id, COUNT(*) as num_wins
        FROM match_score ms
        WHERE ms.team_score2 > ms.team_score1
        GROUP BY ms.team_id2
    ) T
ON t.team_id = T.team_id
GROUP BY t.team_id;

-- counter of AWAY losses
create or replace view count_away_losses as
SELECT t.team_id, COUNT(num_losses) as LOSSES
FROM teams t
    FULL OUTER JOIN
    (
        SELECT ms.team_id2 as team_id, COUNT(*) as num_losses
        FROM match_score ms
        WHERE ms.team_score2 < ms.team_score1
        GROUP BY ms.team_id2
    ) T
ON t.team_id = T.team_id
GROUP BY t.team_id;

-- counter of AWAY draws
create or replace view count_away_draws as
SELECT t.team_id, COUNT(num_draws) as draws
FROM teams t
    FULL OUTER JOIN
    (
        SELECT ms.team_id2 as team_id, COUNT(*) as num_draws
        FROM match_score ms
        WHERE ms.team_score2 = ms.team_score1
        GROUP BY ms.team_id2
    ) T
ON t.team_id = T.team_id
GROUP BY t.team_id;

-- Counting wins, draws and losses for each teams (equipe)
create or replace view count_wins as
SELECT team_id, sum(wins) as wins
FROM (
        SELECT *
        FROM count_home_wins
        UNION ALL
        SELECT *
        FROM count_away_wins
    )
GROUP BY team_id;

create or replace view count_draws as
SELECT team_id, sum(draws) as draws
FROM (
        SELECT *
        FROM count_home_draws
        UNION ALL
        SELECT *
        FROM count_away_draws
    )
GROUP BY team_id;

create or replace view count_losses as
SELECT team_id, sum(LOSSES) as LOSSES
FROM (
        SELECT *
        FROM count_home_losses
        UNION ALL
        SELECT *
        FROM count_away_losses
)
GROUP BY team_id;

-- WIN, DRAW, LOSS Counter for teams
create or replace view team_wdl as
SELECT t.team_id, w.wins, d.draws, l.losses
FROM teams t
    FULL OUTER JOIN count_wins w ON t.team_id = w.team_id
    FULL OUTER JOIN count_draws d ON t.team_id = d.team_id
    FULL OUTER JOIN count_losses l ON t.team_id = l.team_id;

-- WIN, DRAW, LOSS Counter for clubs
create or replace view club_wdl as
SELECT c.*, t.team_id, wins, draws, LOSSES
FROM clubs c, team_wdl wdl, categories cat, teams t
WHERE  c.club_id = t.club_id AND cat.category_id = t.category_id
        AND wdl.team_id = t.team_id;

-- ============================================================
--   VIEWS: Statistiques
-- ============================================================

create or replace view match_statistics as
SELECT match_id, sum(score_accumulation) as match_score, sum(faults_accumulation) as match_faults
FROM match_summary
GROUP BY match_id;

create or replace view season_statistics as
SELECT s.season_id as season_id, sum(match_score) as season_score, sum(match_faults) as season_faults
FROM extended_matches m JOIN seasons s ON m.season_id = s.season_id
    JOIN match_statistics ms ON m.match_id = ms.match_id
GROUP BY s.season_id;