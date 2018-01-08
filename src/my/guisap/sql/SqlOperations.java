package my.guisap.sql;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 *
 *
 *
 * @author DaN
 */
public class SqlOperations {

    public static final String BLOCK_SAVE_SCOPE = "SELECT SCOPE_IDENTITY() NewId";
    public static final String GUIDE = "select a.CODE, a.NAME from SAPX_";
    public static final String SELECT_FOR_CATALOG_FORM = "select a.CODE, a.NAME from ";
    public static final String GUIDE_CAWNT = "select * from SAPX_CAWNT a ";
    public static final String BLOCK_ARTICLE_PR_DELETE = "DELETE FROM BLOCK_ARTICLE_PR WHERE RELIDKEY = ";
    public static final String MODEL_SIZE_DELETE = "DELETE FROM MODEL_SIZE WHERE RELIDKEY = ";
    public static final String BLOCK_ARTICLE_SAVE = "INSERT INTO BLOCK_ARTICLE(IDKEY,CODE,NAME,RELIDKEY,MAIN_CLASS) VALUES ";
    public static final String BLOCK_ARTICLE_PR_SAVE = "INSERT INTO BLOCK_ARTICLE_PR(RELIDKEY,ATNAM,ATBEZ,VALUE) VALUES ";
    public static final String BLOCK_ARTICLE_UPDATE = "UPDATE BLOCK_ARTICLE SET ";
    public static final String ARTICLE_MODEL_GUIDE = "SELECT a.CODE,a.NAME,a.IDKEY FROM BLOCK_MODEL a where a.MAIN_CLASS = ";
    public static final String ARTICLE_HEADER = "select b.ATNAM, b.ATBEZ, b.VALUE, b.RELIDKEY,a.MAIN_CLASS from BLOCK_ARTICLE_PR b INNER JOIN BLOCK_ARTICLE a on rtrim(b.RELIDKEY) = rtrim(a.IDKEY)";
    public static final String BLOCK_FASON_PR_DELETE = "DELETE FROM BLOCK_FASON_PR WHERE RELIDKEY = ";
    public static final String BLOCK_FASON_SAVE = "INSERT INTO BLOCK_FASON(IDKEY,CODE,NAME,MAIN_CLASS) VALUES ";
    public static final String BLOCK_FASON_PR_SAVE = "INSERT INTO BLOCK_FASON_PR(RELIDKEY,ATNAM,ATBEZ,VALUE) VALUES ";
    public static final String FASON_HEADER = "select b.ATNAM, b.ATBEZ, b.VALUE, b.RELIDKEY,a.MAIN_CLASS from BLOCK_FASON_PR b INNER JOIN BLOCK_FASON a on rtrim(b.RELIDKEY) = rtrim(a.IDKEY)";

    public static final String DATA_SELECTION = "select a.ATINN, a.ATNAM, a.ATBEZ, case when a.ATPRT = '' or a.ATPRT is null then  'CAWNT' else a.ATPRT end ATPRT, b.CLASS, b.MAIN_ATNAM, b.PRIORITY,b.MAIN_CLASS from  CLASS_PR a INNER JOIN BLOCK_PR b on rtrim(a.ATNAM) = rtrim(b.ATNAM) and rtrim(a.CLASS) = rtrim(b.CLASS) where b.CLASS= ";
    public static final String DATA_SELECTION_NO_CLASS_RELATION = "select a.ATINN, a.ATNAM, a.ATBEZ, case when a.ATPRT = '' or a.ATPRT is null then  'CAWNT' else a.ATPRT end ATPRT, b.CLASS, b.MAIN_ATNAM, b.PRIORITY,b.MAIN_CLASS from  CLASS_PR a INNER JOIN BLOCK_PR b on rtrim(a.ATNAM) = rtrim(b.ATNAM) where b.CLASS= ";
    public static final String DATA_SELECTION_2 = "select a.ATINN, a.ATNAM, a.ATBEZ, case when a.ATPRT = '' or a.ATPRT is null then  'CAWNT' else a.ATPRT end ATPRT, b.CLASS, b.MAIN_ATNAM, b.PRIORITY,b.MAIN_CLASS from  CLASS_PR a INNER JOIN BLOCK_PR b on rtrim(a.ATNAM) = rtrim(b.ATNAM) where a.CLASS= ";
    public static final String DATA_PF_SELECTION = "select a.ATINN, a.ATNAM, a.ATBEZ, case when a.ATPRT = '' then  'CAWNT' else a.ATPRT end ATPRT, a.CLASS from  CLASS_PR a inner join TEXN_ATNAM b on a.ATNAM = b.ATNAM inner join BLOCK_ARTICLE_GROUPS c on a.CLASS = c.classcode inner join BLOCK_ARTICLE d on c.IDKEY = d.IDKEY inner join BLOCK_MODEL e on e.IDKEY = d.RELIDKEY left join BLOCK_PR f on a.ATNAM = f.ATNAM where e.IDKEY = ";
    public static final String GROUP_BY = "group by b.PRIORITY, a.ATINN, a.ATNAM, a.ATBEZ, a.ATPRT, b.CLASS, b.MAIN_ATNAM,b.MAIN_CLASS order by b.PRIORITY";
    public static final String BLOCK_MODEL_UPDATE = "UPDATE BLOCK_MODEL SET ";
    public static final String BLOCK_MODEL_PR_DELETE = "DELETE FROM BLOCK_MODEL_PR WHERE RELIDKEY = ";
    public static final String BLOCK_MODEL_SAVE = "INSERT INTO BLOCK_MODEL(IDKEY,CODE,NAME,RELIDKEY,MAIN_CLASS) VALUES ";
    public static final String SIZE_MODEL_SAVE = "INSERT INTO MODEL_SIZE(RELIDKEY,CODE,NAME) VALUES ";
    public static final String BLOCK_MODEL_PR_SAVE = "INSERT INTO BLOCK_MODEL_PR(RELIDKEY,ATNAM,ATBEZ,VALUE) VALUES ";
    public static final String MODEL_FASON_GUIDE = "SELECT a.CODE,a.NAME,a.IDKEY FROM BLOCK_FASON a where a.MAIN_CLASS = ";
    public static final String MODEL_HEADER = "select b.ATNAM, b.ATBEZ, b.VALUE, b.RELIDKEY,a.MAIN_CLASS from BLOCK_MODEL_PR b INNER JOIN BLOCK_MODEL a on rtrim(b.RELIDKEY) = rtrim(a.IDKEY)";
    public static final String SIZE_SELECTION = "select CODE, NAME from SAPX_ZTSIZE";
    public static final String SIZE_MODEL_SELECTION = "select RELIDKEY, CODE, NAME from MODEL_SIZE where RELIDKEY = ";
    public static final String MODEL_FASON_NAME = "SELECT a.NAME FROM BLOCK_FASON a where IDKEY = (SELECT b.RELIDKEY FROM BLOCK_MODEL b where IDKEY = ";
    public static final String ARTICLE_MODEL_NAME = "SELECT a.NAME FROM BLOCK_MODEL a where IDKEY = (SELECT b.RELIDKEY FROM BLOCK_ARTICLE b where IDKEY = ";
    public static final String MODEL_FASON_KEY = "SELECT b.IDKEY FROM BLOCK_FASON b where IDKEY = (SELECT a.RELIDKEY FROM BLOCK_MODEL a where IDKEY = ";
    public static final String ARTICLE_MODEL_KEY = "SELECT b.IDKEY FROM BLOCK_MODEL b where IDKEY = (SELECT a.RELIDKEY FROM BLOCK_ARTICLE a where IDKEY = ";
    public static final String MODEL_TECHN_DELETE = "DELETE FROM BLOCK_MODEL_TECHN WHERE RELIDKEY = ";
    public static final String MODEL_TECHN_SAVE = "INSERT INTO BLOCK_MODEL_TECHN(RELIDKEY,ATNAM,ATBEZ,VALUE) VALUES ";
    public static final String MODEL_PF_TECHN_DELETE = "DELETE FROM BLOCK_MODEL_PF_TECHN WHERE RELIDKEY = ";
    public static final String MODEL_PF_TECHN_SAVE = "INSERT INTO BLOCK_MODEL_PF_TECHN(RELIDKEY,ATNAM,ATBEZ,VALUE) VALUES ";
    //public static final String BLOCK_MODEL_TECHN_SELECTION = "select b.ATNAM, b.ATBEZ, b.VALUE from BLOCK_MODEL_TECHN b INNER JOIN (select a.ATINN, a.ATNAM, a.ATBEZ, case when a.ATPRT = '' then  'CAWNT' else a.ATPRT end ATPRT, b.CLASS, b.MAIN_ATNAM, b.PRIORITY from  CLASS_PR a INNER JOIN BLOCK_PR b on rtrim(a.ATNAM) = rtrim(b.ATNAM) where a.CLASS in ('SALE_BW', 'SHOES_BW') and b.CLASS='MOD_TEXN' group by b.PRIORITY, a.ATINN, a.ATNAM, a.ATBEZ, a.ATPRT, b.CLASS, b.MAIN_ATNAM) a on rtrim(a.ATNAM) = rtrim(b.ATNAM) where b.RELIDKEY = ";
    public static final String BLOCK_MODEL_TECHN_SELECTION = "select b.ATNAM, b.ATBEZ, b.VALUE from BLOCK_MODEL_TECHN b INNER JOIN (select a.ATINN, a.ATNAM, a.ATBEZ, case when a.ATPRT = '' then  'CAWNT' else a.ATPRT end ATPRT, b.CLASS, b.MAIN_ATNAM, b.PRIORITY from  CLASS_PR a INNER JOIN BLOCK_PR b on rtrim(a.ATNAM) = rtrim(b.ATNAM) where b.CLASS='MOD_TEXN' group by b.PRIORITY, a.ATINN, a.ATNAM, a.ATBEZ, a.ATPRT, b.CLASS, b.MAIN_ATNAM) a on rtrim(a.ATNAM) = rtrim(b.ATNAM) where b.RELIDKEY = ";
    public static final String BLOCK_MODEL_PF_TECHN_SELECTION = "select b.ATNAM, b.ATBEZ, b.VALUE from BLOCK_MODEL_PF_TECHN b where b.RELIDKEY = ";
    //public static final String CLASS_EDIT_SELECTION = "select b.CLASS MAIN_CLASS,b.ATBEZ,c.CLASS, c.PRIORITY, c.MAIN_ATNAM,b.ATNAM from (select 'SALE_BW' CLASS,a.ATBEZ,a.ATNAM from CLASS_PR a where a.CLASS in ('SALE_BW', 'SHOES_BW') group by a.ATBEZ,a.ATNAM union all select a.CLASS,a.ATBEZ,a.ATNAM from CLASS_PR a where a.CLASS in ('SALE_HW1', 'SALE_SHOES')) b LEFT JOIN BLOCK_PR c on rtrim(c.ATNAM) = rtrim(b.ATNAM) and rtrim(c.MAIN_CLASS) = rtrim(b.CLASS) group by b.CLASS,c.CLASS,c.PRIORITY,b.ATBEZ,c.MAIN_ATNAM,b.ATNAM";
    public static final String CLASS_EDIT_SELECTION = "select b.CLASS MAIN_CLASS,b.ATBEZ,c.CLASS, c.PRIORITY, c.MAIN_ATNAM,b.ATNAM from (select 'SALE_BW' CLASS,a.ATBEZ,a.ATNAM from CLASS_PR a group by a.ATBEZ,a.ATNAM union all select a.CLASS,a.ATBEZ,a.ATNAM from CLASS_PR a) b LEFT JOIN BLOCK_PR c on rtrim(c.ATNAM) = rtrim(b.ATNAM) and rtrim(c.MAIN_CLASS) = rtrim(b.CLASS) group by b.CLASS,c.CLASS,c.PRIORITY,b.ATBEZ,c.MAIN_ATNAM,b.ATNAM";
    public static final String BLOCK_PR_DELETE = "DELETE FROM BLOCK_PR";
    public static final String BLOCK_PR_SAVE = "INSERT INTO BLOCK_PR(ATNAM,CLASS,MAIN_ATNAM,PRIORITY,MAIN_CLASS) VALUES ";
    public static final String PREPACK_EDIT_SELECTION = "select a.NAME Article,b.NAME Model ,ISNULL(c.NAME,'') Kode_fasone_kolodki,isnull(d.VALUE,'') Fasone_kolodki, isnull(e.VALUE,'') Sposob_izgotavleniya,a.IDKEY ID_aticle from BLOCK_ARTICLE a inner join BLOCK_MODEL b on a.RELIDKEY = b.IDKEY left join BLOCK_FASON c on b.RELIDKEY = c.IDKEY left join BLOCK_FASON_PR d on c.IDKEY = d.RELIDKEY  and d.ATNAM = 'FASON_LAST' left join BLOCK_MODEL_PR e on b.IDKEY = e.RELIDKEY and e.ATNAM = 'FACTURE' where a.MAIN_CLASS = 'SALE_BW'";
    public static final String CLASS_GROUP_SELECTION = "SELECT * FROM CLASS_GROUP order by groupcode desc";
    public static final String PREPACK_GROUPCODE_SELECTION_1 = "SELECT a.groupcode, a.ozmName, a.colorfullname,c.SHORT_NAME FROM BLOCK_ARTICLE_GROUPS a INNER JOIN BLOCK_ARTICLE b ON (a.IDKEY = b.IDKEY) left join SHORT_NAME_COLOR_MAT c ON (a.colorfullname = c.NAME)WHERE a.groupcode in ('52202','62202','52203','62203','52401','62401','52402','62402') and a.IDKEY = ";
    public static final String PREPACK_GROUPCODE_SELECTION_2 = " union ALL SELECT a.groupcode, a.ozmName, a.colorfullname, d.SHORT_NAME FROM BLOCK_ARTICLE_GROUPS a INNER JOIN BLOCK_ARTICLE b ON (a.IDKEY = b.IDKEY) left join SHORT_NAME_COLOR_SOLE d on a.colorfullname = d.NAME where a.groupcode in ('52000','62000') and a.IDKEY = ";
    public static final String PREPACK_GROUPCODE_SELECTION_3 = " union all SELECT a.groupcode, a.ozmName, a.colorfullname, d.CODE FROM BLOCK_ARTICLE_GROUPS a INNER JOIN BLOCK_ARTICLE b ON (a.IDKEY = b.IDKEY) left join SAPX_ZTCOLOR_FG d on a.colorfullname = d.NAME where a.groupcode not in ('52000','62000','52202','62202','52203','62203','52401','62401','52402','62402') and a.IDKEY = ";
    public static final String PREPACK_GROUP_DELETE = "delete from BLOCK_ARTICLE_GROUPS where IDKEY = ";
    public static final String PREPACK_GROUP_SAVE = "INSERT INTO BLOCK_ARTICLE_GROUPS(IDKEY,GROUPCODE,CLASSCODE,OZMNAME,COLORFULLNAME) VALUES ";
    public static final String ARTICLE_ATNAM_VALUE_SELECTION_1 = "select b.ATNAM, b.VALUE from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_PR b on a.IDKEY = b.RELIDKEY where a.NAME = '";
    public static final String ARTICLE_ATNAM_VALUE_SELECTION_2 = "' union all select c.ATNAM, c.VALUE from BLOCK_ARTICLE a inner join BLOCK_MODEL b on a.RELIDKEY = b.IDKEY inner join BLOCK_MODEL_PR c on b.IDKEY = c.RELIDKEY where  a.NAME = '";
    public static final String ARTICLE_ATNAM_VALUE_SELECTION_3 = "' union all select d.ATNAM, d.VALUE from BLOCK_ARTICLE a inner join BLOCK_MODEL b on a.RELIDKEY = b.IDKEY inner join BLOCK_FASON c on b.RELIDKEY = c.IDKEY inner join BLOCK_FASON_PR d on c.IDKEY = d.RELIDKEY where  a.NAME = '";
    public static final String ARTICLE_ATNAM_VALUE_SELECTION_4 = "' union ALL select 'ART',a.NAME from BLOCK_ARTICLE a where  a.NAME = '";
    public static final String ARTICLE_ATNAM_VALUE_SELECTION_5 = "' union ALL select 'MODEL',b.NAME from BLOCK_MODEL b inner join BLOCK_ARTICLE a on a.RELIDKEY = b.IDKEY where  a.NAME = '";
    public static final String COLOR_SOLE_SELECTION = "select a.CODE,a.NAME,b.SHORT_NAME from SAPX_ZTCOLOR_SOLE a left join SHORT_NAME_COLOR_SOLE b on a.CODE = b.CODE order by a.NAME";
    public static final String COLOR_MAT_SELECTION = "select a.CODE,a.NAME,b.SHORT_NAME from SAPX_ZTCOLOR_MAT a left join SHORT_NAME_COLOR_MAT b on a.CODE = b.CODE order by a.NAME";
    public static final String SHORT_5F_SELECTION = "select 'Ассортимент' ATBEZ,a.NAME,b.SHORT_NAME from SAPX_T2505 a left join SHORT_NAME_5F_GROUP b on a.NAME = b.NAME group by a.NAME,b.SHORT_NAME union all select 'Половозрастная группа',a.NAME,b.SHORT_NAME from SAPX_T2506 a left join SHORT_NAME_5F_GROUP b on a.NAME = b.NAME group by a.NAME,b.SHORT_NAME union all select 'Материал подкладки',a.NAME,b.SHORT_NAME from SAPX_T2507 a left join SHORT_NAME_5F_GROUP b on a.NAME = b.NAME group by a.NAME,b.SHORT_NAME union all select 'Материал подошвы',a.NAME,b.SHORT_NAME from SAPX_T2508 a left join SHORT_NAME_5F_GROUP b on a.NAME = b.NAME group by a.NAME,b.SHORT_NAME union all select 'Материал верха',a.NAME,b.SHORT_NAME from SAPX_T2510 a left join SHORT_NAME_5F_GROUP b on a.NAME = b.NAME group by a.NAME,b.SHORT_NAME";
    public static final String SHORT_NAME_COLOR_MAT_SAVE = "insert into SHORT_NAME_COLOR_MAT(CODE,NAME,SHORT_NAME) values ";
    public static final String SHORT_NAME_COLOR_SOLE_SAVE = "insert into SHORT_NAME_COLOR_SOLE(CODE,NAME,SHORT_NAME) values ";
    public static final String SHORT_NAME_5F_GROUP_SAVE = "insert into SHORT_NAME_5F_GROUP(ATBEZ,NAME,SHORT_NAME) values ";
    public static final String SHORT_NAME_COLOR_MAT_DELETE = "delete from SHORT_NAME_COLOR_MAT";
    public static final String SHORT_NAME_COLOR_SOLE_DELETE = "delete from SHORT_NAME_COLOR_SOLE";
    public static final String SHORT_NAME_5F_GROUP_DELETE = "delete from SHORT_NAME_5F_GROUP";
    public static final String COLOR_MAT_GUIDE = "SELECT a.NAME,a.SHORT_NAME from SHORT_NAME_COLOR_MAT a where a.SHORT_NAME != '' order by a.NAME";
    public static final String COLOR_SOLE_GUIDE = "SELECT a.NAME,a.SHORT_NAME from SHORT_NAME_COLOR_SOLE a where a.SHORT_NAME != '' order by a.NAME";
    public static final String SHORT_NAME_COLOR_FG = "select a.CODE,a.NAME,a.SHORT_NAME from SHORT_NAME_COLOR_FG a";
    public static final String SHORT_NAME_5F_GROUP = "select a.NAME,a.SHORT_NAME from SHORT_NAME_5F_GROUP a";
    public static final String CHECK_PREPACK_SELECTION = "select a.groupcode from BLOCK_ARTICLE_GROUPS a where a.IDKEY = ";
    public static final String BLOCK_ARTICLE_GRUOPS_UPDATE = "UPDATE BLOCK_ARTICLE_GROUPS SET ";
    public static final String FULL_RAKURS_SELECTION1 = "select distinct * from (select DISTINCT a.NAME Art, b.ozmName NAME, b.groupcode GROUP,case when a.MAIN_CLASS = 'SALE_BW' then 'SHOES_BW' else a.MAIN_CLASS end MAIN_CLASS from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_GROUPS b on a.IDKEY = b.IDKEY where  b.groupcode = 'NF' union all select distinct c.NAME, d.ozmName, d.groupcode, c.MAIN_CLASS from MODEL_SIZE a inner join BLOCK_MODEL b on a.RELIDKEY = b.IDKEY inner join BLOCK_ARTICLE c on b.IDKEY = c.RELIDKEY inner join BLOCK_ARTICLE_GROUPS d on d.IDKEY = c.IDKEY where d.groupcode = 'FERT' union  ALL select d.NAME, b.ozmName, b.groupcode, b.classcode from BLOCK_ARTICLE_GROUPS b inner join BLOCK_ARTICLE d on b.IDKEY = d.IDKEY where b.groupcode NOT IN ('NF', 'FERT')) c order by Art,c.NAME";
    public static final String FULL_RAKURS_SELECTION = "exec SAP_NSI.dbo.GET_RAKURS_TABLE";
    public static final String ONLY_RAKURS_SELECTION = "select * from RAKURS a inner join VID_RAKURS b on a.ID = b.ID_RAKURS where R_VALUE = 'ZZZ' order by a.ID";
    public static final String SELECT_ARTICLE_W_GROUPS = "SELECT DISTINCT a.NAME FROM BLOCK_ARTICLE a INNER JOIN BLOCK_ARTICLE_GROUPS b ON (a.IDKEY = b.IDKEY) order by a.NAME";
    public static final String SELECT_ARTICLE_GROUPS = "SELECT a.groupcode FROM BLOCK_ARTICLE_GROUPS a WHERE a.IDKEY = (SELECT b.IDKEY FROM BLOCK_ARTICLE b WHERE b.NAME = ";
    public static final String BLOCK_RAKURS_DELETE = "delete from BLOCK_RAKURS";
    public static final String BLOCK_RAKURS_INSERT = "INSERT INTO BLOCK_RAKURS(ART,NAME,RAKURS,VALUE,GROUPMAT) VALUES ";
    public static final String BLOCK_RAKURS_UPDATE = "UPDATE BLOCK_RAKURS SET VALUE = ";
    public static final String BLOCK_RAKURS_CHECK_EXIST = "EXECUTE SAP_NSI..BLOCK_RAKURS_CHECK_EXIST ?, ?";
    public static final String BLOCK_RAKURS_SELECTION = "select a.* from BLOCK_RAKURS a inner join BLOCK_ARTICLE b on a.ART = b.NAME order by b.IDKEY,a.NAME, a.RAKURS desc";
    public static final String SAVING_RAKURS_SELECTION = "SELECT a.ID, a.VID_MAT, a.SUBVID_MAT, a.MAT_GROUP, a.ID_RAKURS, b.ID Expr1, b.R_NAME, b.R_VALUE, b.R_DESCR, b.R_MANUAL FROM VID_RAKURS a INNER JOIN RAKURS b ON a.ID_RAKURS = b.ID WHERE a.SUBVID_MAT = LEFT(?,2)";
    public static final String SAVING_RAKURS_FERT_SELECTION = "SELECT a.ID, a.VID_MAT, a.SUBVID_MAT, a.MAT_GROUP, a.ID_RAKURS, b.ID Expr1, b.R_NAME, b.R_VALUE, b.R_DESCR, b.R_MANUAL FROM VID_RAKURS a INNER JOIN RAKURS b ON a.ID_RAKURS = b.ID where ID_RAKURS = ?";
    public static final String SAVING_RAKURS_ATTR_SELECTION = "select 'ART' Priznak, a.NAME Znachenie from BLOCK_ARTICLE a where a.NAME = ? union all select b.ATNAM,b.VALUE from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_PR b on a.IDKEY = b.RELIDKEY where a.NAME = ? union all select 'MODEL',a.NAME from BLOCK_ARTICLE b inner join BLOCK_MODEL a on a.IDKEY = b.RELIDKEY where b.NAME = ? union all select a.ATNAM,a.VALUE from BLOCK_ARTICLE b inner join BLOCK_MODEL c on c.IDKEY = b.RELIDKEY inner join BLOCK_MODEL_PR a on a.RELIDKEY = c.IDKEY where b.NAME = ? union all select 'CODE_LAST',a.NAME from BLOCK_ARTICLE b inner join BLOCK_MODEL c on c.IDKEY = b.RELIDKEY inner join BLOCK_FASON a on a.IDKEY = c.RELIDKEY where b.NAME = ? union all select a.ATNAM,a.VALUE from BLOCK_ARTICLE b inner join BLOCK_MODEL c on c.IDKEY = b.RELIDKEY inner join BLOCK_FASON d on d.IDKEY = c.RELIDKEY inner join BLOCK_FASON_PR a on a.RELIDKEY = d.IDKEY where b.NAME = ? union ALL select a.ATNAM, a.VALUE from BLOCK_MODEL_TECHN a inner join BLOCK_MODEL b on a.RELIDKEY = b.IDKEY inner join BLOCK_ARTICLE c on c.RELIDKEY = b.IDKEY where c.NAME = ? union all select a.ATNAM, a.VALUE from BLOCK_MODEL_PF_TECHN a inner join BLOCK_MODEL b on a.RELIDKEY = b.IDKEY inner join BLOCK_ARTICLE c on c.RELIDKEY = b.IDKEY where c.NAME = ?";
    public static final String SAVING_RAKURS_OZM_5UPK_INSERT = "INSERT INTO OZM_5UPK(MATNR,MAKTX,MATKL,MEINS,DISGR,DISMM,DISPO,BISMT,DISLS,BESKZ,SOBSL,RGEKZ,KZECH,DZEIT,FHORI,PERKZ,MTVFP,ALTSL,FEVOR,SFCPF,XCHPF,CLASS_001,MODEL_001,MAT_LINING_001,FASON_001,SEASON_001,ASSORTMENT_001,GROUPMANWOMAN_001) VALUES (";
    public static final String SAVING_RAKURS_OZM_HLB1_INSERT = "INSERT INTO OZM_HLB1(MATNR,DWERK,MAKTX,MEINS,MATKL,DISGR,DISMM,DISPO,BISMT,DISLS,BESKZ,SOBSL,RGEKZ,KZECH,DZEIT,FHORI,PERKZ,MTVFP,ALTSL,FEVOR,SFCPF,XCHPF,BWTTY,MLAST,BKLAS,VPRSV,PVPRS_1,PVPRS_2,PEINH,EKALR,HKMAT,AWSLS,HRKFT,KOSGR,LOSGR,FXHOR,CLASS_001,COLOR_FG_001,ART_001,MODEL_001,MAT_LINING_001,FASON_LAST_001,CODE_LAST_001,METHOD_CONSTR_001,SHOE_WIDTH_001,METOD_FORM_001,CONSTR_BOTTOM_001,TYPE_SOLE_001,CONSTR_EYELET_001,TYPE_REGUL_001,FASON_001,SEASON_001,ASSORTMENT_001,GROUPMANWOMAN_001,MAT_SOLE_001,MAT_UPPER_001,HIGH_HEEL_001,FASON_SOLE_001,CODE_SOLE_001,FASON_HEEL_001,MAT_UPPER_GR_001,FACTURE_001,CLASS_023) VALUES (";
    public static final String SAVING_RAKURS_OZM_5F_INSERT = "INSERT INTO OZM_5F(MATNR,MAKTX,MATKL,MEINS,DISGR,DISMM,DISPO,BISMT,DISLS,BESKZ,SOBSL,RGEKZ,KZECH,DZEIT,FHORI,PERKZ,MTVFP,ALTSL,FEVOR,SFCPF,XCHPF,CLASS_001,COLOR_FG_001,ART_001,MODEL_001,MAT_LINING_001,FASON_LAST_001,CODE_LAST_001,METHOD_CONSTR_001,SHOE_WIDTH_001,METOD_FORM_001,CONSTR_BOTTOM_001,TYPE_SOLE_001,CONSTR_EYELET_001,TYPE_REGUL_001,FASON_001,SEASON_001,ASSORTMENT_001,GROUPMANWOMAN_001,MAT_SOLE_001,MAT_UPPER_001,HIGH_HEEL_001,FASON_SOLE_001,CODE_SOLE_001,FASON_HEEL_001,MAT_UPPER_GR_001,FACTURE_001,CLASS_023) VALUES (";
    public static final String SAVING_RAKURS_OZM_5UPK_DELETE = "delete from OZM_5UPK where MAKTX = '";
    public static final String SAVING_RAKURS_OZM_HLB1_DELETE = "delete from OZM_HLB1 where MAKTX = '";
    public static final String SAVING_RAKURS_OZM_5F_DELETE = "delete from OZM_5F where MAKTX = '";
    public static final String SAVING_RAKURS_CODE_SELECTING = "select a.COUNT from OZM_SPR_GROUP_56VM a where a.GROUP_KOD = '";
    public static final String SAVING_RAKURS_CODE_UPDATE = "UPDATE OZM_SPR_GROUP_56VM SET COUNT = ? where GROUP_KOD = ?";
    public static final String SAVING_RAKURS_OZM_6VM_DELETE = "delete from OZM_6VM where MAKTX = '";
    public static final String SAVING_RAKURS_OZM_6VM_CHECKING = "select * from OZM_6VM where MAKTX = ?";
    public static final String SAVING_RAKURS_OZM_6VM_INSERT = "INSERT INTO OZM_6VM(MATNR,MAKTX,MATKL,WERKS,LGORT,MEINS,DISGR,DISMM,DISPO,BSTMI,DISLS,BESKZ,SOBSL,RGEKZ,KZECH,DZEIT,FHORI,PERKZ,MTVFP,ALTSL,FEVOR,SFCPF,XCHPF,FXHOR,BWTTY,MLAST,BKLAS,VPRSV,PVPRS_1,PVPRS_2,VERPR,PEINH,EKALR,HKMAT,AWSLS,HRKFT,KOSGR,LOSGR,CLASS_001,COLOR_FG_001,MODEL_001,ART_001,IMAGE_001,MAT_LINING_001,FASON_LAST_001,CODE_LAST_001,METHOD_CONSTR_001,SHOE_WIDTH_001,METOD_FORM_001,CONSTR_BOTTOM_001,CONSTR_SOLE_001,CONSTR_EYELET_001,TYPE_EYELET_001,TYPE_REGUL_001,TYPE_SOLE_001,FASON_001,SEASON_001,ASSORTMENT_001,GROUPMANWOMAN_001,MAT_SOLE_001,MAT_UPPER_001,HIGH_HEEL_001,FASON_SOLE_001,CODE_SOLE_001,FASON_HEEL_001,MAT_UPPER_GR_001,FASON_BACK_001,CONSTR_VAMP_001,CONSTR_INGOT_001,DECOR_INGOT_001,TYPE_DECOR_001,CONSTR_KANT_001,CONSTR_INSOLE_001,TYPE_PROC_001,COLOR_MAT_001,COLOR_SOLE_001,CONSTR_UNIT_001,CONSTR_HALFINSOLE_001,CONSTR_LATEX_001,PROTECTOR_001,FACTURE_001,CLASS_023) VALUES (";
    public static final String SAVING_RAKURS_OZM_5VM_DELETE = "delete from OZM_5VM where MAKTX = '";
    public static final String SAVING_RAKURS_OZM_5VM_INSERT = "INSERT INTO OZM_5VM(MATNR,MAKTX,MATKL,MEINS,DISGR,DISMM,DISPO,BISMT,DISLS,BESKZ,SOBSL,RGEKZ,KZECH,DZEIT,FHORI,PERKZ,MTVFP,ALTSL,FEVOR,SFCPF,XCHPF,CLASS_001,COLOR_FG_001,MODEL_001,ART_001,MAT_LINING_001,FASON_LAST_001,CODE_LAST_001,METHOD_CONSTR_001,SHOE_WIDTH_001,METOD_FORM_001,CONSTR_BOTTOM_001,CONSTR_SOLE_001,CONSTR_EYELET_001,TYPE_EYELET_001,TYPE_REGUL_001,TYPE_SOLE_001,FASON_001,SEASON_001,ASSORTMENT_001,GROUPMANWOMAN_001,MAT_SOLE_001,MAT_UPPER_001,HIGH_HEEL_001,FASON_SOLE_001,CODE_SOLE_001,FASON_HEEL_001,MAT_UPPER_GR_001,FASON_BACK_001,CONSTR_VAMP_001,CONSTR_INGOT_001,DECOR_INGOT_001,TYPE_DECOR_001,CONSTR_KANT_001,CONSTR_INSOLE_001,TYPE_PROC_001,COLOR_MAT_001,COLOR_SOLE_001,CONSTR_UNIT_001,CONSTR_HALFINSOLE_001,CONSTR_LATEX_001,PROTECTOR_001,FACTURE_001,CLASS_023) VALUES (";
    public static final String MODEL_EDITION_YEAR_SELECTING = "select a.VALUE from BLOCK_MODEL_PR a where a.ATNAM = 'EDITION_YEAR' and a.RELIDKEY = ";
    public static final String SIZE_CODE_SELECTING = "select a.NAME from SAPX_ZTFSIZE a where a.CODE = ?";
    public static final String SAVING_RAKURS_OZM_FERT_INSERT = "INSERT INTO OZM_FERT(MATNR,NEWKUA,COLORSKR,MAKTX,MEINS,BISMT,MTART,MTPOS_MARA,PRDHA,NTGEW,BRGEW,GEWEI,VOLUM,VOLEH,SPART,VKORG,WERKS,VTWEG,SKTOF,MEGRU,TAXKM,SCMNG,SCHME,RDPRF,VERSG,BONUS,KONDM,KTGRM,MTPOS,PRAT1,PRAT2,MTVFP,XCHPF,TRAGR,LADGR,MAGRV,LTKZA,LGBKZ,LHMG1,LHME1,LETY1,LHMG2,LHME2,LETY2,RDMNG,MAMNG,LGTYP,BWTTY,MLAST,BKLAS,VPRSV,PVPRS_1,PVPRS_2,VERPR,PEINH,EKALR,HKMAT,AWSLS,HRKFT,KOSGR,LOSGR,STAWN,SCAN,EAN11,INNOV_YEAR,STARTUP,CLASS_001,COLOR_FG_001,SIZE_001,ART_001,MODEL_001,FASON_001,SEASON_001,ASSORTMENT_001,GROUPMANWOMAN_001,MAT_LINING_001,MAT_SOLE_001,MAT_UPPER_001,HIGH_HEEL_001,FASON_SOLE_001,CODE_SOLE_001,FASON_LAST_001,CODE_LAST_001,FASON_HEEL_001,METHOD_CONSTR_001,SHOE_WIDTH_001,MAT_UPPER_GR_001,LINE_001,GUARANTEE_001,IMPORT_001,INNOVATION_001,NEW_001,DESIGNER_001,CODE_INDUSTRY_001,STAT_KOD_001,MANUFACTOR_001,COUNTRY_PROD_001,FACTURE_001,CLASS_023) VALUES (";
    public static final String SAVING_RAKURS_OZM_FERT_DELETE = "delete from OZM_FERT where MAKTX = '";
    public static final String UPLOAD_MAT_SELECTION_NOT_UPLOAD = "select * from NOT_UPLOAD ";
    public static final String MODEL_SIZE_ROW_SELECTION = "select b.NAME from BLOCK_MODEL a inner join MODEL_SIZE b on a.IDKEY = b.RELIDKEY where b.RELIDKEY = ? ORDER BY b.NAME";
    public static final String REPORT_ART_SELECTION = "select a.NAME from BLOCK_ARTICLE a";
    public static final String REPORT_ATTR_SELECTION = "select 'Артикул' Priznak, a.NAME Znachenie from BLOCK_ARTICLE a where a.NAME = ? union all select b.ATBEZ,b.VALUE from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_PR b on a.IDKEY = b.RELIDKEY where a.NAME = ? union all select 'Модель',a.NAME from BLOCK_ARTICLE b inner join BLOCK_MODEL a on a.IDKEY = b.RELIDKEY where b.NAME = ? union all select a.ATBEZ,a.VALUE from BLOCK_ARTICLE b inner join BLOCK_MODEL c on c.IDKEY = b.RELIDKEY inner join BLOCK_MODEL_PR a on a.RELIDKEY = c.IDKEY where b.NAME = ? union all select 'Код фасона колодки',a.NAME from BLOCK_ARTICLE b inner join BLOCK_MODEL c on c.IDKEY = b.RELIDKEY inner join BLOCK_FASON a on a.IDKEY = c.RELIDKEY where b.NAME = ? union all select a.ATBEZ,a.VALUE from BLOCK_ARTICLE b inner join BLOCK_MODEL c on c.IDKEY = b.RELIDKEY inner join BLOCK_FASON d on d.IDKEY = c.RELIDKEY inner join BLOCK_FASON_PR a on a.RELIDKEY = d.IDKEY where b.NAME = ?";
    public static final String REPORT_ATBEZ_SELECTION = "select distinct a.ATBEZ from CLASS_PR a inner join BLOCK_PR b on a.ATNAM = b.ATNAM where b.MAIN_CLASS = 'SALE_BW' and b.CLASS != 'MOD_TEXN' union all select 'Год выпуска'";
    public static final String KOROB_SELECTION = "select a.CODE,a.NAME,b.SHORT_NAME from SAPX_ZTKOROB a left join SHORT_KOROB b on a.CODE = b.CODE order by a.NAME";
    public static final String KOROB_DELETE = "delete from SHORT_KOROB";
    public static final String KOROB_SAVE = "insert into SHORT_KOROB(CODE,NAME,SHORT_NAME) values ";
    public static final String SELECT_KOROB_VALUE = "SELECT DISTINCT a.NAME, a.SHORT_NAME FROM SHORT_KOROB a where a.SHORT_NAME != '' order by a.NAME";
    public static final String COLOR_FG_SELECTION = "select a.CODE,a.NAME,b.SHORT_NAME from SAPX_ZTCOLOR_FG a left join SHORT_NAME_COLOR_FG b on a.CODE = b.CODE order by a.NAME";
    public static final String COLOR_FG_DELETE = "delete from SHORT_NAME_COLOR_FG";
    public static final String COLOR_FG_SAVE = "insert into SHORT_NAME_COLOR_FG(CODE,NAME,SHORT_NAME) values ";
    public static final String SELECT_COLOR_FG_VALUE = "SELECT DISTINCT a.NAME, a.SHORT_NAME FROM SHORT_NAME_COLOR_FG a where a.SHORT_NAME != ''";
    public static final String TECHN_REPORT = "select * from GET_TECHN_REPORT";
    public static final String SELECT_SECURITY_GROUP_KEY = "SELECT a.PRIVILEGE_KEY FROM USERS_GROUPS a WHERE a.ID_GROUP = ?";
    public static final String REQUEST_SAPX_SAVING = "insert into REQUEST_SAPX (ATBEZ, VALUE, USERS, COMP, SAVE_FLAG, EDIT_VALUE) Select ?, ?, ?, ?, ?, ? from dual where not exists(select * from REQUEST_SAPX where ATBEZ=? and VALUE = ? and USER = ? and COMP = ? and SAVE_FLAG = ? and EDIT_VALUE = ?)";
    public static final String REQUEST_SAPX_REPORT = "select distinct a.COMP \"Имя машины\", a.USERS \"Пользователь\", a.DATES \"Дата заявки\", case when b.ATPRT = '' then  'CAWNT' else b.ATPRT end \"Таблица\", a.ATBEZ \"Признак модели\", a.VALUE \"Значение заявки\", a.EDIT_VALUE \"Итоговое знач.\",  case when a.SAVE_FLAG = 1 then 'Заведен' when a.SAVE_FLAG = 2 then 'Отклонен' when a.SAVE_FLAG = 3 then 'Скорректирован и заведен' else 'Не заведен' end Status,a.SAVE_FLAG from REQUEST_SAPX a inner join CLASS_PR b on a.ATBEZ = b.ATBEZ";
    public static final String SELECT_HISTORY_BLOCK_PR = "select distinct a.ATBEZ, a.ATNAM from CLASS_PR a inner join BLOCK_PR b on a.ATNAM = b.ATNAM where b.CLASS = ? and b.MAIN_CLASS = ? and b.PRIORITY != 1";
    public static final String SELECT_HISTORY_BLOCK_PR_TECHN = "select distinct a.ATBEZ, a.ATNAM from CLASS_PR a inner join BLOCK_PR b on a.ATNAM = b.ATNAM where b.CLASS = ? and b.MAIN_CLASS = ?";
    public static final String GET_SCALA_DATA = "EXECUTE SAP_NSI..GET_SCALA_DATA ?";
    public static final String INSERT_LOGS = "INSERT INTO LOGS(TIME,COMPUTER,USERS,ACTION) VALUES (";
    public static final String GET_LOGS_BY_DATE = "SELECT * FROM LOGS WHERE to_char(time,'yyyymmdd') = ? ORDER BY TIME";
    public static final String GET_LOGS_ALL = "SELECT to_char(TIME,'dd-mm-yyyy  hh24:mi:ss') times,computer,users,action FROM LOGS order by time DESC";
    public static final String PRICE_DATA = "select distinct c.GROUP_NAME Group_mat, case when d.VALUE is null then '' else d.VALUE end Cena from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_GROUPS b on a.IDKEY = b.IDKEY inner join GROUPS c on c.GROUP_CODE = b.groupcode left join (select a.GROUP_NAME, b.VALUE from GROUPS a inner join BLOCK_RAKURS b on a.GROUP_CODE = b.GROUPMAT where b.RAKURS = 'стандартная цена' and b.ART = ?) d on d.GROUP_NAME = c.GROUP_NAME where a.NAME = ? and (b.groupcode = 'NF' or substr(b.groupcode, 1, 1) = '6')";
    public static final String WEIGHT_VOLUM_PRICE_REPORT = "select distinct a.NAME Article from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_GROUPS b on a.IDKEY = b.IDKEY";
    public static final String WEIGHT_VOLUM_REPORT = "select distinct a.NAME Article, 'Не проставлены значения объема и массы' val from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_GROUPS b on a.IDKEY = b.IDKEY where a.NAME not in (select distinct b.ART from BLOCK_RAKURS b where b.RAKURS in ('масса брутто','масса нетто','объем'))";
    public static final String PRICE_REPORT = "select distinct a.NAME Article, 'Не проставлены цены' val from BLOCK_ARTICLE a inner join BLOCK_ARTICLE_GROUPS b on a.IDKEY = b.IDKEY where a.NAME not in (select distinct b.ART from BLOCK_RAKURS b where b.RAKURS = 'стандартная цена')";

    public static final String LAST_LIST_TO_EDIT = "select DISTINCT INDEX_LAST \"Индекс колодки\",FASON_LAST \"Фасон колодки\",DESIGNER \"Модельер\",GENDER \"Пол\", VALUE_PPCH \"Величина ППЧ\", YEAR \"Год\",TYPE_LAST \"Тип колодки\",PRINCIPLE_CONSTRUCTION \"Принцип постр.\",OUTLINE_TRACK \"Абрис следа\", FORM_TOE_PIECE \"Форма носка\", COMPLETENESS_LAST \"Полнота колодки\",VOLUME_BEAM \"Объем пучков\",WIDTH_BEAM \"Ширина пучков\", WIDTH_BACK \"Ширина пятки\", LENTH_TRACK \"Длина следа\",CONSTRUCTION_SOLE_NODE \"Констр. под. узл.\",PLACE \"Адрес ячейки хр.\" from GUI_SAP.LAST_HEAD";
    public static final String LAST_LIST_TABLE = "select DISTINCT DESIGNER \"Модельер\",FASON_LAST \"Фасон колодки\",INDEX_LAST \"Индекс колодки\",GENDER \"Пол\", VALUE_PPCH \"Величина ППЧ\", YEAR \"Год\",TYPE_LAST \"Тип колодки\",PRINCIPLE_CONSTRUCTION \"Принцип постр.\",OUTLINE_TRACK \"Абрис следа\", FORM_TOE_PIECE \"Форма носка\", COMPLETENESS_LAST \"Полнота колодки\",VOLUME_BEAM \"Объем пучков\",WIDTH_BEAM \"Ширина пучков\", WIDTH_BACK \"Ширина пятки\", LENTH_TRACK \"Длина следа\",CONSTRUCTION_SOLE_NODE \"Констр. под. узл.\",PLACE \"Адрес ячейки хр.\", DATE_CREATE \"Дата создания\" from GUI_SAP.LAST_HEAD";
    public static final String LAST_REQUEST = "select MODEL \"Модель\", DATE_CREATE \"Дата заявки\",  DESIGNER \"Художник\", FASON_LAST \"Фасон колодки\", TYPE_LAST \"Тип колодки\", CONSTRUCTION_SOLE_NODE \"Кн. подшв. узла\", FASON_SOLE \"Фасон подошвы\", FASON_HEEL \"Фасон каблука\", MAT_LINING \"Мат. подкладки\", COMPLETENESS_FOOT \"Полн. ноги носч.\", INFO \"Примечания\" from LAST_REQUEST ";
    public static final String LAST_REQUEST_OPEN_STATUS = " where STATUS='OP'";
    public static final String LAST_REQUEST_CLOSE_STATUS = " where STATUS='CL'";
    public static final String LAST_ORDER_BY = " ORDER BY DATE_CREATE DESC";

    public static final String MKZ_LIST = "select DISTINCT MODEL \"Модель\",YEAR \"Год\",GROUPMANWOMAN \"Пол\",METHOD_CONSTR \"Метод крепления\",SHIN_VIEW \"Вид обуви\",SEASON \"Сезон\",STYLE_MODEL \"Стиль модели\",SIMILAR_MODEL \"Модель аналог\",MAT_LINING \"Тип подкладки\",FASON_SOLE \"Фасон подошвы\",FASON_LAST \"Фасон колодки\",FASON_HEEL \"Фасон каблука\" ,DESIGNER \"Художник\",case when STATUS = 'C' then 'Рассмотрен' when Status = 'F' then 'Первый этап' when status = 'W' then 'На рассмотрении' when status = 'N' then 'Второй этап' when status = 'L' then 'Сформирована заявка на колодку' when status = 'LC' then 'Заявка на колодку рассмотрена' when status = 'R' then 'Отклонено' when status = 'R_U' then 'Отклонено (на доработку)' end \"Статус\", DATE_UPDATE \"Дата изменения\" from GUI_SAP.MKZ_LIST_HEAD ";
    public static final String MKZ_LIST_CLOSE_FOR_CALCUL = "select DISTINCT MODEL \"Модель\",YEAR \"Год\",GROUPMANWOMAN \"Пол\",METHOD_CONSTR \"Метод крепления\",SHIN_VIEW \"Вид обуви\",SEASON \"Сезон\" from GUI_SAP.MKZ_LIST_HEAD where status = 'C'";
    public static final String MKZ_LIST_ALL = " where status not like 'R' and status not like 'C' ORDER BY DATE_UPDATE DESC";
    public static final String MKZ_LIST_FIRST = " where status = 'F' ORDER BY DATE_UPDATE DESC";
    public static final String MKZ_LIST_SECOND = " where status = 'N' ORDER BY DATE_UPDATE DESC";
    public static final String MKZ_LIST_WORK = " where status = 'W' ORDER BY DATE_UPDATE DESC";
    public static final String MKZ_LIST_CLOSE = " where status = 'C' ORDER BY DATE_UPDATE DESC";
    public static final String MKZ_LIST_REJECT = " where status = 'R' OR status = 'R_U' ORDER BY DATE_UPDATE DESC";
    public static final String MKZ_LIST_LAST = " where status = 'L' OR status = 'LC' ORDER BY DATE_UPDATE DESC";
    public static final String MKZ_LIST_ARH = " where status = 'ARH'";
    public static final String MKZ_LIST_LAST_NOT_CLOSED = " where status = 'L' ORDER BY DATE_UPDATE DESC";
    public static final String UNION_REQUEST_SAPX = "UNION ALL SELECT case when SAVE_FLAG = 1 then 'Заведен' when SAVE_FLAG = 2 then 'Отклонен' when SAVE_FLAG = 3 then 'Скорректирован и заведен' else 'Не заведен' end, EDIT_VALUE FROM REQUEST_SAPX where SAVE_FLAG!=2 and ";

    public static final String UD_DETAILS_FULL_TABLE = "SELECT '' \"Изображение\", SKETCH \"Эскиз констр.\",INDEX_UP \"Индекс констр.\", SEASON \"Сезон\", GENDER \"Пол изделия\" , TYPE_PRODUCT \"Тип изделия\",TYPE_LINING \"Тип подкладки\", TYPE_CONSTRUCTION_UP \"Тип констр.врх.\" ,FASON_LAST \"Фасон колодки\" , WAY_OF_DEV \"Способ разраб.\", HEIGHT_CONSTRUCTION \"Выс. конструкции\",   VOLUME_BOOTLEG_1 \"Об. голенища №1\", VOLUME_BOOTLEG_2 \"Об. голенища №2\", VOLUME_BOOTLEG_3 \"Об. голенища №3\",    METHOD_ATTACHMENT_CONSTRUCTION \"Сп. крепл. конст.\",METHOD_TIGHTENING \"Сп. зат. конст.\"     ,case when STATUS = 'NS' then 'Ожидание добавления в SAP' when STATUS = 'R' then 'Заявка' when Status = 'S' then 'Данные заведены в SAP' end \"Статус\" FROM LB_UP_DETAILS ORDER BY ID";
    public static final String UD_DETAILS_FULL = "SELECT * FROM LB_UP_DETAILS";
    public static final String SOLE_FULL_TABLE = "SELECT '' \"Изображение\", NAME \"Фасон подошвы\",ART \"Артикул\", GENDER \"Пол\" , MATERIAL \"Материал\",PROVIDER \"Изготовитель\", COLOR_SURFACE \"Цвет ход. пов.\" , COLOR_MAIN \"Цвет ос.масс. под\" , COLOR_RANT \"Цвет ранта\", DESCRIPTION_TRIM \"Опис. отделки\", ROSTOVKA \"Ростовка\", TYPE_SOLE \"Тип подошвы\", STYLE_SOLE \"Стиль подошвы\", VALUE_PPCH \"Величина ППЧ\",CONSTRUCTION \"Конструкция\", PRINCIPLE_CONSTRUCTION \"Прин. построения\" , OUTLINE_TOE_PIECE \"Абрис\", TEMPLATE_TRACK \"Шаблон следа\",COMPLETENESS_SOLE \"Толщина в пучках\",WIDTH_SOLE \"Ширина в пучках\",WIDTH_SOLE_BACK \"Ширина в пятке\", LENTH_TRACK \"Длина следа\", PLACE \"Адрес ячейки хр.\" ,case when STATUS = 'NS' then 'Ожидание добавления в SAP' when STATUS = 'R' then 'Заявка' when Status = 'S' then 'Данные заведены в SAP' end \"Статус\" FROM LB_SOLE ORDER BY ID";
    public static final String SOLE_FULL = "SELECT * FROM LB_SOLE";
    public static final String HEEL_FULL_TABLE = "SELECT '' \"Изображение\",name \"Фасон каблука\",ART   \"Артикул каблука\", material \"Мат. каблука\", provider  \"Изготовитель\",COLOR_MAIN  \"Цвет каблука\", TYPE_OF_FINISH \"Тип отделки\",rostovka    \"Ростовка\",CONSTRUCTION \"Конструкция\", HEIGHT   \"Высота каблука\", VPPCHK \"ВППЧК\",STYLE_HEEL \"Стиль каблука\",DESCRIPTION_HEEL \"Общее описание\", PLACE \"Адрес ячейки хр.\", case when STATUS = 'NS' then 'Ожидание добавления в SAP' when STATUS = 'R' then 'Заявка' when Status = 'S' then 'Данные заведены в SAP' end \"Статус\" FROM LB_HEEL ORDER BY ID";
    public static final String HEEL_FULL = "SELECT * FROM LB_HEEL ORDER BY ID";
    public static final String INSOLE_FULL = "SELECT NAME \"Фасон стельки\",MATERIAL \"Материал\",PROVIDER \"Изготовитель\",ROSTOVKA \"Ростовка\",CONSTRUCTION \"Конструкция\" ,case when STATUS = 'NS' then 'Ожидание добавления в SAP' when STATUS = 'R' then 'Заявка' when Status = 'S' then 'Данные заведены в SAP' end \"Статус\" FROM LB_BASIC_INSOLE ORDER BY ID";

    public static final String EC_MAIN = "SELECT ID \"ИД\", TYPE_PART \"Тип партии\", MODEL \"Модель\", ART \"Артикул\", COUNT \"Количество\", SIZE_SHOE \"Количество\", NOTE \"Примечание\", DIRECTED \"Направлено\", DATE_CREATE \"Дата создания\" from EC";

    private Statement st;
    private ResultSet rs;
    private JTable table = new JTable();
    ConnectionClass connect;
    
    public static final SqlOperations sql = new SqlOperations();

    public SqlOperations() {
        
    }
    
    
    

    public int dataUpdate(String query) {
        return dataUpdate_1(query, 0);
    }

    private int dataUpdate_1(String query, int count) {
        int idKey = 0;
        if (count == 10) {
            for (int pos = 0; pos < query.length(); pos += 70) {
                query = query.substring(0, pos) + "\n" + query.substring(pos, query.length());
            }
            problem("Данная функция dataUpdate \nнакрутила себя 10 раз...\n" + query);
            return idKey;
        }
        try {
            st = ConnectionClass.CONNECT_BUF.createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (SQLException ex) {
            System.out.println("Error dataUpdate_1 ");
            System.out.println(ex + "\\\\" + query);
            dataUpdate_1(query, count + 1);
        }
        try {
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                idKey = rs.getInt(1);
            }
        } catch (SQLException exc) {
        }

        return idKey;
    }

    public void tableFill(String query, DefaultTableModel model) {
        System.out.println(query);
        tableFill_1(query, model, 0);
    }

    private void tableFill_1(String query, DefaultTableModel model, int count) {
        if (count == 10) {
            for (int pos = 0; pos < query.length(); pos += 70) {
                query = query.substring(0, pos) + "\n" + query.substring(pos, query.length());
            }
            problem("Данная функция tableFill \nнакрутила себя 10 раз...\n" + query);
            model = null;
        } else {
            table.setModel(model);
            try {
                st = connect.CONNECT_BUF.createStatement();
                rs = st.executeQuery(query);
                setDataTable(rs, model);
            } catch (SQLException ex) {
                System.out.println(ex);
                tableFill_1(query, model, count + 1);

            } catch (NullPointerException ex) {
                System.out.println(ex);
            } finally {
                closeStRs(st, rs);
            }
        }
    }

    public void fillModel(String query, DefaultTableModel model, int count) {
        if (count == 10) {
            for (int pos = 0; pos < query.length(); pos += 70) {
                query = query.substring(0, pos) + "\n" + query.substring(pos, query.length());
            }
            problem("Данная функция tableFill \nнакрутила себя 10 раз...\n" + query);
            model = null;
        } else {
            table.setModel(model);
            try {
                st = connect.CONNECT_BUF.createStatement();
                rs = st.executeQuery(query);
                addToDataTable(rs, model);
            } catch (SQLException ex) {
                fillModel(query, model, count + 1);
            } catch (NullPointerException ex) {
                System.out.println(ex);
            } finally {
                closeStRs(st, rs);
            }
        }
    }

    public void setDataTable(ResultSet rs, DefaultTableModel model) throws SQLException {
        int ColumnCount;

        ColumnCount = rs.getMetaData().getColumnCount();
        model.setColumnCount(0);
        for (int colInd = 1; colInd < ColumnCount + 1; colInd++) {
            model.addColumn(rs.getMetaData().getColumnName(colInd).toString());
        }
        int rowInd = 0, rsInd;
        while (rs.next()) {
            rsInd = 1;
            ((DefaultTableModel) table.getModel()).addRow(new Object[]{});
            for (int colInd = 0; colInd < ColumnCount; colInd++) {
                table.setValueAt(rs.getString(rsInd), rowInd, colInd);
                rsInd++;
            }
            rowInd++;
        }
    }

    public void addToDataTable(ResultSet rs, DefaultTableModel model) throws SQLException {
        if (model.getColumnCount() == 0) {
            int ColumnCount;
            ColumnCount = rs.getMetaData().getColumnCount();
            model.setColumnCount(0);
            for (int colInd = 1; colInd < ColumnCount + 1; colInd++) {
                model.addColumn(rs.getMetaData().getColumnName(colInd).toString());
            }
        }
        int rowInd = model.getRowCount(), rsInd;
        while (rs.next()) {
            rsInd = 1;
            ((DefaultTableModel) table.getModel()).addRow(new Object[]{});
            for (int colInd = 0; colInd < model.getColumnCount(); colInd++) {
                table.setValueAt(rs.getString(rsInd), rowInd, colInd);
                rsInd++;
            }
            rowInd++;
        }
    }

    public CallableStatement callingTableFill(String query) {
        return callingTableFill_1(query, 0);
    }

    private CallableStatement callingTableFill_1(String query, int count) {
        if (count == 10) {
            for (int pos = 0; pos < query.length(); pos += 70) {
                query = query.substring(0, pos) + "\n" + query.substring(pos, query.length());
            }
            problem("Данная функция callingTableFill \nнакрутила себя 10 раз...\n" + query);
            return null;
        } else {
            try {
                return connect.CONNECT_BUF.prepareCall(query);
            } catch (SQLException ex) {
                callingTableFill_1(query, count + 1);
            } catch (NullPointerException ex) {
                System.out.println(ex);
            }
            return null;
        }
    }

    public void csTableFill(DefaultTableModel model, CallableStatement cs) {
        csTableFill_1(model, cs, 0);
    }

    private void csTableFill_1(DefaultTableModel model, CallableStatement cs, int count) {
        if (count == 10) {

            problem("Данная функция csTableFill \nнакрутила себя 10 раз...");
        } else {
            table.setModel(model);
            try {
                rs = cs.executeQuery();
                setDataTable(rs, model);
                rs.close();
            } catch (SQLException ex) {
                csTableFill_1(model, cs, count + 1);
            } catch (NullPointerException exc) {
            } finally {
                closeStRs(st, rs);
            }
        }
    }

    public PreparedStatement preparingTableFill(String query) {
        return preparingTableFill_1(query, 0);
    }

    private PreparedStatement preparingTableFill_1(String query, int count) {

        if (count == 10) {
            for (int pos = 0; pos < query.length(); pos += 70) {
                query = query.substring(0, pos) + "\n" + query.substring(pos, query.length());
            }
            problem("Данная функция preparingTableFill \nнакрутила себя 10 раз...\n" + query);
            return null;
        }

        try {
            PreparedStatement ps = connect.CONNECT_BUF.prepareStatement(query);
            return ps;
        } catch (SQLException ex) {
            preparingTableFill_1(query, count + 1);
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public void psTableFill(DefaultTableModel model, PreparedStatement ps) {
        for (int i = 0; i < model.getDataVector().size(); i++) {
            System.out.println(model.getDataVector().get(i).toString());
        }
        psTableFill_1(model, ps, 0);
    }

    private void psTableFill_1(DefaultTableModel model, PreparedStatement ps, int count) {
        if (count == 10) {
            problem("Данная функция psTableFill \nнакрутила себя 10 раз...");
        } else {
            table.setModel(model);
            try {
                rs = ps.executeQuery();
                setDataTable(rs, model);
            } catch (SQLException ex) {
                System.out.println(ex);
                psTableFill_1(model, ps, count + 1);
            } catch (NullPointerException exc) {

            } finally {
                closeStRs(null, rs);
            }
        }

    }

    public String strFill(String query) {
        return strFill_1(query, 0);
    }

    private String strFill_1(String query, int count) {

        String str = "";
        if (count == 10) {
            for (int pos = 0; pos < query.length(); pos += 70) {
                query = query.substring(0, pos) + "\n" + query.substring(pos, query.length());
            }
            problem("Данная функция strFill \nнакрутила себя 10 раз...\n" + query);
            return str;
        }

        try {
            st = connect.CONNECT_BUF.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                str = rs.getString(1);
            }
        } catch (SQLException ex) {
            strFill_1(query, count + 1);
        } finally {
            closeStRs(st, rs);
        }

        return str;
    }

    public void SendQuery(String query) {
        System.out.println("---------------------");
        System.out.println(query);
        System.out.println("---------------------");
        try {
            st = ConnectionClass.CONNECT_BUF.createStatement();
            st.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Ошибка в SendQuery" + ex);
            System.out.println(query);
        } finally {
            closeStRs(st, null);
        }
    }

    public String psStrFill(PreparedStatement ps) {
        return psStrFill_1(ps, 0);
    }

    private String psStrFill_1(PreparedStatement ps, int count) {

        String str = "";
        if (count == 10) {
            problem("Данная функция csStrFill \nнакрутила себя 10 раз...");
            return str;
        }
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                str = rs.getString(1);
            }
        } catch (SQLException ex) {
            psStrFill_1(ps, count + 1);
        } finally {
            closeStRs(null, rs);
        }

        return str;
    }

    public Blob getBlob(String sql) {
        Blob result = null;
        try {
            st = ConnectionClass.CONNECT_BUF.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                result = rs.getBlob(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeStRs(st, rs);
        }
        return result;
    }

    public Object getObj(String sql) {
        Object result = null;
        try {
            st = ConnectionClass.CONNECT_BUF.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                result = rs.getObject(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeStRs(st, rs);
        }
        return result;
    }

    /**
     * Проверка на наличие в бд
     *
     * @param sql запрос
     * @return true - есть false - нету
     */
    public boolean checkInfo(String sql) {
        boolean result = false;
        try {
            st = ConnectionClass.CONNECT_BUF.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeStRs(st, rs);
        }
        return result;
    }

    public static int problem(String str) {
        Object[] options = {"Ходить через стены", "Пятерное сальто назад"};

        int answer = JOptionPane.showOptionDialog(null,
                str,
                "Хватай бубен",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);
        return answer;
    }

    private void closeStRs(Statement st, ResultSet rs) {
        try {
            st.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException e) {

        }
    }

    public DefaultTableModel getColumnsFormTable(String[] exceptionFields, String nameTable) {
        DefaultTableModel result = new DefaultTableModel();

        StringBuilder exception = new StringBuilder();
        for (String exceptionField : exceptionFields) {
            exception.append(" and column_name not like '").append(exceptionField).append("'");
        }

        tableFill("SELECT column_name"
                + " FROM USER_TAB_COLUMNS"
                + " WHERE table_name = '" + nameTable + "' " + exception.toString(), result);

        return result;
    }

}
