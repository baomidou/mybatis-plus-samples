
CREATE PROCEDURE p_alter_table_column_add(tableName VARCHAR(200),columnName VARCHAR(200),sqlStr VARCHAR(1000))
BEGIN
DECLARE Rows1 INT(10);
SET Rows1=0;
SELECT COUNT(*) INTO Rows1 FROM INFORMATION_SCHEMA.Columns WHERE table_schema= DATABASE() AND table_name=tableName AND column_name=columnName;

IF (Rows1<=0) THEN
SET sqlStr = CONCAT( 'ALTER TABLE ', tableName, ' ADD COLUMN ', columnName, ' ', sqlStr);
END IF;

IF (sqlStr!='') THEN
SET @sql1 = sqlStr;
PREPARE stmt1 FROM @SQL1;
EXECUTE stmt1;
END IF;
END;$$
