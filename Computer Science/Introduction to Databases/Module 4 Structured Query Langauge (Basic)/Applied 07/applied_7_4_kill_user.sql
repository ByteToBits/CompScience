
-- Kills a Specific User of a Locking a Session

--  SID    SERIAL# STATUS   STATE               Logon Time          
------------ ---------- -------- ------------------- --------------------
--   395      17228 INACTIVE WAITING             MAR27 00:34         
--   587      28617 ACTIVE   WAITED SHORT TIME   MAR27 00:35 

-- begin
--    kill_own_session(1526,52369);
-- end;

DECLARE
    sid     NUMBER;
    serial# NUMBER;
    CURSOR inactive_sessions IS
    SELECT
        sid,
        serial#
    FROM
        v$session
    WHERE
        type = 'USER'
        AND username = user
        AND upper(osuser) != 'ORACLE'
        AND status = 'INACTIVE';
BEGIN
    OPEN inactive_sessions;
    LOOP
        FETCH inactive_sessions INTO sid, serial#;
        EXIT WHEN inactive_sessions%notfound;
        kill_own_session(sid, serial#);
    END LOOP;

    CLOSE inactive_sessions;
END;
/