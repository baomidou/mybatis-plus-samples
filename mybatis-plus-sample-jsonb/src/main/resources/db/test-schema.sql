-- ----------------------------
-- Table structure for test_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."test_data";
CREATE TABLE "public"."test_data" (
                                      "id" int8 NOT NULL,
                                      "content" jsonb
)
;

-- ----------------------------
-- Primary Key structure for table test_data
-- ----------------------------
ALTER TABLE "public"."test_data" ADD CONSTRAINT "test_data_pkey" PRIMARY KEY ("id");
