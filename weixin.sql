-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.0.18-nt - MySQL Community Edition (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- 正在导出表  weixin.activity 的数据：16 rows
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` (`aid`, `type`, `activityType`, `baoMingUplimit`, `activityBeginTime`, `activityEndTime`, `baoMingBeginTime`, `baoMingEndTime`, `score`, `qrcodeUrl`, `name`, `description`, `state`, `pid`, `hid`, `geoid`, `index4project`, `index4geo`, `index4house`, `synchronize`) VALUES
	('23f9ee28-e6be-4183-87cb-7fd30df7b012', '1', '2', -1, 1543366800000, 1543374000000, 1543295064297, 1543334400000, 1, 'qrcode\\2\\11\\23f9ee28-e6be-4183-87cb-7fd30df7b012.gif', '为老志愿服务', '为老志愿服务', '已完成', '402881e4675310a00167532225470008', '402881e4675310a00167538cc3b80012', NULL, 0, NULL, 0, b'0'),
	('10941f90-4b48-4c5b-a060-c7a348b0bbdc', '1', '2', -1, 1543888800000, 1543892400000, 1543824118465, 1543852800000, 1, 'qrcode\\14\\13\\10941f90-4b48-4c5b-a060-c7a348b0bbdc.gif', '核桃园社区元旦晚会', '核桃园社区元旦晚会', '已完成', '402881e4675310a00167532225470008', '402881e4675310a00167538cc3b80012', NULL, 1, NULL, 1, b'0'),
	('5fad65a0-6b01-4133-a5aa-45c6cd2f9d00', '1', '2', -1, 1543892400000, 1543896000000, 1543824530976, 1543852800000, 2, 'qrcode\\2\\3\\5fad65a0-6b01-4133-a5aa-45c6cd2f9d00.gif', '关东店社区新年庆祝会', '关东店社区新年庆祝会', '已完成', '402881e4675310a00167534982a20010', '402881e4677311910167731c44040000', NULL, 0, NULL, 0, b'0'),
	('fee5018b-ea93-4cef-a513-aa9bbe0506cd', '1', '2', -1, 1544662800000, 1544670000000, 1544600646762, 1544630400000, 0, 'qrcode\\6\\11\\fee5018b-ea93-4cef-a513-aa9bbe0506cd.gif', '核桃园测试活动', '核桃园测试活动', '已完成', '402881e4675310a0016753237441000a', '402881e4675310a00167538cc3b80012', NULL, 0, NULL, 2, b'0'),
	('262cd67c-6009-47e0-894e-e0931d52b404', '1', '2', -1, 1544680800000, 1544688000000, 1544601987907, 1544630400000, 0, 'qrcode\\7\\3\\262cd67c-6009-47e0-894e-e0931d52b404.gif', '核桃园测试活动', '核桃园测试活动', '已完成', '402881e4675310a00167532225470008', '402881e4675310a00167538cc3b80012', NULL, 2, NULL, 3, b'0'),
	('68ec9003-9176-44f3-b218-c1af49d0db11', '1', '2', -1, 1546650000000, 1546660800000, 1546495426201, 1546617600000, 1, 'qrcode\\11\\9\\68ec9003-9176-44f3-b218-c1af49d0db11.gif', '为老志愿服务', '为社区老年人提供志愿服务', '已完成', '402881e4675310a00167532225470008', '402881e4675310a00167538cc3b80012', NULL, 3, NULL, 4, b'0'),
	('590e1fec-e519-4768-adce-aad72eaa4cb3', '1', '2', -1, 1548727200000, 1548730800000, 1548667204778, 1548691200000, 1, 'qrcode\\10\\1\\590e1fec-e519-4768-adce-aad72eaa4cb3.gif', '小年大扫除', '小年大扫除', '已完成', '402881e4675310a00167532225470008', '402881e4675310a00167538cc3b80012', NULL, 4, NULL, 5, b'0'),
	('fd86169c-579b-4213-83d1-9e41626da4f6', '1', '2', -1, 1548903600000, 1548907200000, 1548816486436, 1548864000000, 2, 'qrcode\\14\\2\\fd86169c-579b-4213-83d1-9e41626da4f6.gif', '测试活动1', '测试活动1', '已完成', '402881e468a1b12d0168a1bb5a410002', '402881e468a1b12d0168a1c25d1b000a', NULL, 0, NULL, 0, b'0'),
	('ba078724-2d90-4d2e-ae06-cf9750f4d9ad', '1', '2', -1, 1548910800000, 1548914400000, 1548822990016, 1548864000000, 1, 'qrcode\\9\\15\\ba078724-2d90-4d2e-ae06-cf9750f4d9ad.gif', '测试活动2', '测试活动2', '已完成', '402881e468a1b12d0168a1bb5a410002', '402881e468a1b12d0168a1c25d1b000a', NULL, 1, NULL, 1, b'0'),
	('046e2849-270e-4cca-9a51-7d32eed3e5e8', '1', '2', -1, 1548914400000, 1548918000000, 1548823031074, 1548864000000, 1, 'qrcode\\11\\10\\046e2849-270e-4cca-9a51-7d32eed3e5e8.gif', '测试活动3', '测试活动3', '进行中', '402881e468a1b12d0168a1bb5a410002', '402881e468a1b12d0168a1c25d1b000a', NULL, 2, NULL, 2, b'0'),
	('40da48aa-43ab-4964-a0c0-565316780dc6', '1', '2', -1, 1548918000000, 1548921600000, 1548823073110, 1548864000000, 1, 'qrcode\\0\\9\\40da48aa-43ab-4964-a0c0-565316780dc6.gif', '测试活动4', '测试活动4', '即将开始', '402881e468a1b12d0168a1bb5a410002', '402881e468a1b12d0168a1c25d1b000a', NULL, 3, NULL, 3, b'0'),
	('93725548-a9ac-427d-ad8e-0e2e14dbb7ff', '1', '2', -1, 1548921600000, 1548925200000, 1548823100760, 1548864000000, 1, 'qrcode\\8\\9\\93725548-a9ac-427d-ad8e-0e2e14dbb7ff.gif', '测试活动5', '测试活动5', '即将开始', '402881e468a1b12d0168a1bb5a410002', '402881e468a1b12d0168a1c25d1b000a', NULL, 4, NULL, 4, b'0'),
	('ff3261e7-9767-4160-ad98-5410f3bb3f2c', '1', '2', -1, 1548984600000, 1548991800000, 1548915763957, 1548950400000, 1, 'qrcode\\1\\5\\ff3261e7-9767-4160-ad98-5410f3bb3f2c.gif', '新年联欢', '新年联欢', '筹备中', '402881e468a1b12d0168a1bb5a410002', '402881e468a1b12d0168a1c25d1b000a', NULL, 5, NULL, 5, b'0'),
	('de1e69fd-5bf4-4226-8dc2-d79e0e0dc576', '1', '2', -1, 1554861600000, 1554865200000, 1554794078440, 1554825600000, 2, 'qrcode\\11\\15\\de1e69fd-5bf4-4226-8dc2-d79e0e0dc576.gif', '测试-同步签到', '测试-同步签到', '已完成', '402881e769db46d10169db4a680a0004', '402881e76a00c465016a00d38edd0007', NULL, 1, NULL, 1, b'1'),
	('ce22f1fd-8ae9-4086-ae01-6d4eaeb5fa13', '1', '2', -1, 1554858000000, 1554861600000, 1554794038760, 1554825600000, 2, 'qrcode\\6\\5\\ce22f1fd-8ae9-4086-ae01-6d4eaeb5fa13.gif', '测试-分开签到', '测试-分开签到', '已完成', '402881e769db46d10169db4a680a0004', '402881e76a00c465016a00d38edd0007', NULL, 0, NULL, 0, b'0'),
	('a8d93bf9-83a8-4229-bc5f-061fee4fbf85', '1', '2', -1, 1557882000000, 1557889200000, 1557814667217, 1557849600000, 2, 'qrcode\\13\\9\\a8d93bf9-83a8-4229-bc5f-061fee4fbf85.gif', '测试活动', '测试活动', '筹备中', '402881e769a317430169a332a5e60000', '402881e86ab4f517016ab4fc3feb0000', NULL, 0, NULL, 0, b'0');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;

-- 正在导出表  weixin.approve4userjoinlevel 的数据：15 rows
/*!40000 ALTER TABLE `approve4userjoinlevel` DISABLE KEYS */;
INSERT INTO `approve4userjoinlevel` (`a4ujlid`, `timeStr`, `timeStamp`, `beread`, `tag`, `lid`) VALUES
	('402881e4687d8da501687da0d9da0000', NULL, 0, b'0', 'first', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e4687e77cc01687ea11e1a0000', NULL, 0, b'0', 'first', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e4687e77cc01687eac7d570003', NULL, 0, b'0', 'first', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e4687e77cc01687ebf7eaf0005', NULL, 0, b'0', 'first', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e4688e641901688e6f9e150000', '2019-01-27 16:36:17', 1548578177778, b'0', 'first', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e4688e641901688e750ad70002', NULL, 0, b'0', 'first', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e468a1b12d0168a1be82840006', '2019-01-31 10:31:46', 1548901906352, b'0', 'minus_first', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e468a1b12d01689c9edf58000b', '2019-01-30 10:38:33', 1548815913102, b'0', 'zero', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e4689d05480168a28fc4e50002', '2019-01-31 14:19:56', 1548915596035, b'0', 'zero', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e76a00c465016a00d0c0280002', '2019-04-09 14:38:00', 1554791880342, b'0', 'zero', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e76a42c6e5016a42d694ff0000', '2019-04-22 10:19:12', 1555899552915, b'0', 'zero', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e76a42c6e5016a42d9fbc00003', '2019-04-22 10:22:44', 1555899764113, b'0', 'minus_first', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e86ab44be1016ab45a21dc0001', '2019-05-14 11:19:51', 1557803991849, b'0', 'minus_first', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e86ab4f517016ab501607b0002', '2019-05-14 14:22:43', 1557814963967, b'0', 'zero', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e86ad92f4c016ad9328e8e0000', '2019-05-21 15:02:23', 1558422143450, b'0', 'zero', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4');
/*!40000 ALTER TABLE `approve4userjoinlevel` ENABLE KEYS */;

-- 正在导出表  weixin.article 的数据：20 rows
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` (`artid`, `content`, `title`, `readingNum`, `forwardingNum`) VALUES
	('23f9ee28-e6be-4183-87cb-7fd30df7b012', '$.trim(str) \n返回：string； \n说明：去掉字符串首尾空格。 \n示例： \n先看个错误代码错误代码： \n复制代码代码如下:\n<input type="text" name="" id="results" value=""/> \nvar content = $(\'#content\').val(); \nif(content.trim() == \'\') \nalert(\'空\'); \n\n上面的写法在firefox下不会报错，但在ie下确会报错 \n所以要写成下面格式： \n复制代码代码如下:\n\n<input type="text" name="" id="results" value=""/> \n\nvar content = $(\'#results\').val(); \nif($.trim(content) == \'\') \nalert(\'空\'); \n\n\n或者 \n复制代码代码如下:\n\nvar content = $(\'#content\').val(); \nif(jQuery.trim(content) == \'\') \nalert(\'空\'); ', '这是一篇测试新闻稿2', 0, 0),
	('10941f90-4b48-4c5b-a060-c7a348b0bbdc', '', '', 0, 0),
	('5fad65a0-6b01-4133-a5aa-45c6cd2f9d00', 'aaaaaaaaaaaaaa', 'aaaa', 0, 0),
	('fee5018b-ea93-4cef-a513-aa9bbe0506cd', '', '', 0, 0),
	('262cd67c-6009-47e0-894e-e0931d52b404', '', '', 0, 0),
	('68ec9003-9176-44f3-b218-c1af49d0db11', '', '', 0, 0),
	('590e1fec-e519-4768-adce-aad72eaa4cb3', '', '', 0, 0),
	('fd86169c-579b-4213-83d1-9e41626da4f6', '', '', 0, 0),
	('ba078724-2d90-4d2e-ae06-cf9750f4d9ad', '', '', 0, 0),
	('046e2849-270e-4cca-9a51-7d32eed3e5e8', '', '', 0, 0),
	('40da48aa-43ab-4964-a0c0-565316780dc6', '', '', 0, 0),
	('93725548-a9ac-427d-ad8e-0e2e14dbb7ff', '', '', 0, 0),
	('ff3261e7-9767-4160-ad98-5410f3bb3f2c', '新年大联欢', '新年大联欢', 0, 0),
	('46d7a119-ad4e-4436-abf6-16a0ed55ede1', '', '', 0, 0),
	('7d13d783-aecf-41ac-88dd-7e533800481f', '', '', 0, 0),
	('d5dde5c9-01a7-4f76-8397-a02555f8a856', '', '', 0, 0),
	('39d0d18e-25d8-4aa0-b6cf-c6a4123fc50d', '', '', 0, 0),
	('ce22f1fd-8ae9-4086-ae01-6d4eaeb5fa13', '', '', 0, 0),
	('de1e69fd-5bf4-4226-8dc2-d79e0e0dc576', '', '', 0, 0),
	('a8d93bf9-83a8-4229-bc5f-061fee4fbf85', '123', '123', 0, 0);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;

-- 正在导出表  weixin.articlephoto 的数据：8 rows
/*!40000 ALTER TABLE `articlephoto` DISABLE KEYS */;
INSERT INTO `articlephoto` (`apid`, `description`, `path`, `artid`, `index4article`) VALUES
	('0336eb85-5194-482c-bd92-83f56236ea02', '', 'upload/10/6/志愿者积分兑换模型.png', '23f9ee28-e6be-4183-87cb-7fd30df7b012', 1),
	('4ea9f019-8d55-4196-91c1-b77969d59d6b', '', 'upload/5/6/80e1d9dcd100baa1be627a3b4d10b912c9fc2e3b.jpg', '23f9ee28-e6be-4183-87cb-7fd30df7b012', 0),
	('069367ad-0dc2-4fc8-b939-85c634a7e60f', '', 'upload/9/11/11111.png', '23f9ee28-e6be-4183-87cb-7fd30df7b012', 3),
	('5b0fc529-d421-4494-afae-2f67d50961da', '', 'upload/6/4/红包.jpg', '23f9ee28-e6be-4183-87cb-7fd30df7b012', 2),
	('ef71e4ba-78f7-4df5-9c17-da7193f4bd11', '', 'upload/7/10/全国文明城区测评体系_（与街道相关内容分析）.png', 'ff3261e7-9767-4160-ad98-5410f3bb3f2c', 0),
	('ea043e62-382a-463e-a226-fba54d5d01f5', '', 'upload/8/5/测评指标的对策项目_（可行方案）.png', 'ff3261e7-9767-4160-ad98-5410f3bb3f2c', 1),
	('ab6b4ac2-8ea6-4dd8-abef-ea6b8a90952e', '', 'upload/0/6/红包.jpg', '5fad65a0-6b01-4133-a5aa-45c6cd2f9d00', 0),
	('c12f5eb6-47a7-4c77-83dc-6276020f1d9b', '', 'upload/1/3/中期沟通会2.jpg', 'a8d93bf9-83a8-4229-bc5f-061fee4fbf85', 0);
/*!40000 ALTER TABLE `articlephoto` ENABLE KEYS */;

-- 正在导出表  weixin.besureproject 的数据：19 rows
/*!40000 ALTER TABLE `besureproject` DISABLE KEYS */;
INSERT INTO `besureproject` (`bpid`, `name`, `description`, `ptid`, `state`, `examinedTime`, `commitTime`, `updateNum`, `startTime`, `endTime`, `activityTotal`, `mflid`, `zid`, `flid`, `scid`, `thid`, `filePath`, `laborCost`, `purchaseCost`) VALUES
	('402881e4675310a00167539bdd350017', '默认项目', '用作老李头为老服务队层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543295982886, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', '9dfdca3e-60a9-4f5e-b777-69c95656f607', NULL, 0, 0),
	('402881e4675310a00167539827310015', '默认项目', '用作青少年志愿者层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543295739697, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '60ae939f-b6cb-4685-ab63-f2bd0b3246b1', NULL, NULL, 0, 0),
	('402881e4675310a00167534982a20011', '默认项目', '用作关东店层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543290585715, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e', NULL, NULL, NULL, NULL, 0, 0),
	('402881e4675310a001675325f5f3000f', '默认项目', '用作老张头为老服务队层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543288255972, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', NULL, NULL, 0, 0),
	('402881e4675310a001675324886a000d', '默认项目', '用作老年志愿者层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543288162410, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', NULL, NULL, NULL, 0, 0),
	('402881e4675310a0016753237441000b', '默认项目', '用作志愿者层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543288091713, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL, NULL, 0, 0),
	('402881e4675310a00167532225470009', '默认项目', '用作核桃园层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543288005959, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e4675310a00167532162d90007', '默认项目', '用作呼家楼层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543287956185, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e4677311910167731f33610002', '默认项目', '用作关东店志愿者层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1543824683864, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e', 'f6e43211-473d-4246-8281-e0fca8b6a7c7', NULL, NULL, NULL, 0, 0),
	('402881e4681221b30168125fef0f0001', '默认项目', '用作为老服务志愿者层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1546496503555, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', NULL, NULL, NULL, 0, 0),
	('402881e468a1b12d0168a1b45bd00001', '默认项目', '用作朝阳门层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1548901178243, 0, 0, 0, 0, 'd5718f48-2387-46c3-8c48-78d520540fd3', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e468a1b12d0168a1bb5a410003', '默认项目', '用作朝内头条社区层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1548901636672, 0, 0, 0, 0, 'd5718f48-2387-46c3-8c48-78d520540fd3', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e769a317430169a332a5e60001', '默认项目', '用作东大桥层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1553221199253, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e769a317430169a33320110003', '默认项目', '用作社区社会组织层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1553221230607, 0, 0, 0, 0, 'd5718f48-2387-46c3-8c48-78d520540fd3', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb', NULL, NULL, NULL, NULL, 0, 0),
	('402881e769db46d10169db489a3d0001', '默认项目', '用作老山街道层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1554162162085, 0, 0, 0, 0, 'fd86918c-d0df-4edb-bf15-e808144b48cc', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e769db46d10169db48cc380003', '默认项目', '用作苹果园街道层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1554162175026, 0, 0, 0, 0, '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e769db46d10169db4a680a0005', '默认项目', '用作边府社区层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1554162280457, 0, 0, 0, 0, '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', NULL, NULL, NULL, NULL, NULL, 0, 0),
	('402881e76a00c465016a00d206080006', '默认项目', '用作志愿者层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1554791925217, 0, 0, 0, 0, '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', 'e4b6911e-cbb1-462f-8a2b-9682d360c026', 'a64e55a9-09a0-4fb0-9a5c-29f748240fc5', NULL, NULL, NULL, 0, 0),
	('402881e86ab4f517016ab524b9a10006', '默认项目', '用作啊啊啊啊层级对象默认使用的确认项目对象', '402881e466d26ccd0166d26cf4960002', NULL, 0, 1557817244063, 0, 0, 0, 0, '47944819-f5ce-41cb-96da-a33ad8dc0284', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68', '9a517058-41b6-4edc-9464-f26760b54399', NULL, NULL, NULL, 0, 0);
/*!40000 ALTER TABLE `besureproject` ENABLE KEYS */;

-- 正在导出表  weixin.doingproject 的数据：19 rows
/*!40000 ALTER TABLE `doingproject` DISABLE KEYS */;
INSERT INTO `doingproject` (`dpid`, `laborCost`, `lastLaborCost`, `bpid`, `mflid`, `zid`, `flid`, `scid`, `thid`) VALUES
	('402881e4675310a00167539bdd350016', 0, 0, '402881e4675310a00167539bdd350017', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', '9dfdca3e-60a9-4f5e-b777-69c95656f607'),
	('402881e4675310a00167534982a20010', 0, 0, '402881e4675310a00167534982a20011', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e', NULL, NULL, NULL),
	('402881e4675310a00167539827310014', 0, 0, '402881e4675310a00167539827310015', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '60ae939f-b6cb-4685-ab63-f2bd0b3246b1', NULL),
	('402881e4675310a001675325f5f3000e', 0, 0, '402881e4675310a001675325f5f3000f', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e4675310a001675324886a000c', 0, 0, '402881e4675310a001675324886a000d', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', NULL),
	('402881e4675310a0016753237441000a', 0, 0, '402881e4675310a0016753237441000b', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', NULL, NULL),
	('402881e4675310a00167532162d90006', 0, 0, '402881e4675310a00167532162d90007', '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL),
	('402881e4675310a00167532225470008', 0, 0, '402881e4675310a00167532225470009', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL),
	('402881e4677311910167731f33610001', 0, 0, '402881e4677311910167731f33610002', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e', 'f6e43211-473d-4246-8281-e0fca8b6a7c7', NULL, NULL),
	('402881e4681221b30168125fef0f0000', 0, 0, '402881e4681221b30168125fef0f0001', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', 'bca0fb80-843d-4b95-9d51-0283116cdb20', NULL),
	('402881e468a1b12d0168a1b45bd00000', 0, 0, '402881e468a1b12d0168a1b45bd00001', 'd5718f48-2387-46c3-8c48-78d520540fd3', NULL, NULL, NULL, NULL),
	('402881e468a1b12d0168a1bb5a410002', 0, 0, '402881e468a1b12d0168a1bb5a410003', 'd5718f48-2387-46c3-8c48-78d520540fd3', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb', NULL, NULL, NULL),
	('402881e769a317430169a332a5e60000', 0, 0, '402881e769a317430169a332a5e60001', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68', NULL, NULL, NULL),
	('402881e769a317430169a33320110002', 0, 0, '402881e769a317430169a33320110003', 'd5718f48-2387-46c3-8c48-78d520540fd3', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb', 'f5d29da4-6532-4830-b780-b8d5cdf8845f', NULL, NULL),
	('402881e769db46d10169db489a3d0000', 0, 0, '402881e769db46d10169db489a3d0001', 'fd86918c-d0df-4edb-bf15-e808144b48cc', NULL, NULL, NULL, NULL),
	('402881e769db46d10169db48cc370002', 0, 0, '402881e769db46d10169db48cc380003', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', NULL, NULL, NULL, NULL),
	('402881e769db46d10169db4a680a0004', 0, 0, '402881e769db46d10169db4a680a0005', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', 'e4b6911e-cbb1-462f-8a2b-9682d360c026', NULL, NULL, NULL),
	('402881e76a00c465016a00d206000005', 0, 0, '402881e76a00c465016a00d206080006', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', 'e4b6911e-cbb1-462f-8a2b-9682d360c026', 'a64e55a9-09a0-4fb0-9a5c-29f748240fc5', NULL, NULL),
	('402881e86ab4f517016ab524b9a10005', 0, 0, '402881e86ab4f517016ab524b9a10006', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68', '9a517058-41b6-4edc-9464-f26760b54399', NULL, NULL);
/*!40000 ALTER TABLE `doingproject` ENABLE KEYS */;

-- 正在导出表  weixin.exchange 的数据：0 rows
/*!40000 ALTER TABLE `exchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange` ENABLE KEYS */;

-- 正在导出表  weixin.firstlevel 的数据：5 rows
/*!40000 ALTER TABLE `firstlevel` DISABLE KEYS */;
INSERT INTO `firstlevel` (`flid`, `name`, `description`, `level`, `qrcode`, `qrcodeTime`, `zid`) VALUES
	('a2828df0-7155-4005-afa3-7f694b75a1af', '志愿者', '核桃园志愿者', 1, 'qrcode\\3\\1\\tag=first&lid=a2828df0-7155-4005-afa3-7f694b75a1af.gif', 1543288091713, 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('f6e43211-473d-4246-8281-e0fca8b6a7c7', '关东店志愿者', '关东店志愿者', 1, 'qrcode\\3\\2\\tag=first&lid=f6e43211-473d-4246-8281-e0fca8b6a7c7.gif', 1543824683828, 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('f5d29da4-6532-4830-b780-b8d5cdf8845f', '社区社会组织', '社区社会组织', 1, 'qrcode\\7\\4\\tag=first&lid=f5d29da4-6532-4830-b780-b8d5cdf8845f.gif', 1553221230593, 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('a64e55a9-09a0-4fb0-9a5c-29f748240fc5', '志愿者', '志愿者', 1, 'qrcode\\5\\14\\tag=first&lid=a64e55a9-09a0-4fb0-9a5c-29f748240fc5.gif', 1554791925185, 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('9a517058-41b6-4edc-9464-f26760b54399', '啊啊啊啊', '啊啊啊啊', 1, 'qrcode\\14\\0\\tag=first&lid=9a517058-41b6-4edc-9464-f26760b54399.gif', 1557817244031, 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68');
/*!40000 ALTER TABLE `firstlevel` ENABLE KEYS */;

-- 正在导出表  weixin.fourthlevel 的数据：2 rows
/*!40000 ALTER TABLE `fourthlevel` DISABLE KEYS */;
INSERT INTO `fourthlevel` (`foid`, `name`, `description`, `level`, `qrcode`, `qrcodeTime`, `thid`) VALUES
	('abb8c3b7-ad77-4730-aedb-1475dcaa0772', '老张头为老服务队第1队', '老张头为老服务队第1队', 4, 'qrcode\\8\\7\\tag=fourth&lid=abb8c3b7-ad77-4730-aedb-1475dcaa0772.gif', 1543288348090, '64c60390-8018-4e24-a064-5c18657a67da'),
	('4c021fae-1fb2-48a0-a09b-b650e647e225', '老李头为老服务队第1队', '老李头为老服务队第1队', 4, 'qrcode\\2\\13\\tag=fourth&lid=4c021fae-1fb2-48a0-a09b-b650e647e225.gif', 1543296226624, '64c60390-8018-4e24-a064-5c18657a67da');
/*!40000 ALTER TABLE `fourthlevel` ENABLE KEYS */;

-- 正在导出表  weixin.geographic 的数据：1 rows
/*!40000 ALTER TABLE `geographic` DISABLE KEYS */;
INSERT INTO `geographic` (`geoid`, `level`, `longitude`, `latitude`, `radus`, `description`, `name`, `enable`, `mflid`, `zid`, `flid`, `scid`, `thid`, `foid`, `index4first`, `index4fourth`, `index4minus`, `index4second`, `index4third`, `index4zero`) VALUES
	('402881e4675310a00167538ed9570013', 0, 42.12312312, 33.23123123, 0, '小花园', '小花园', b'1', NULL, 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `geographic` ENABLE KEYS */;

-- 正在导出表  weixin.grouping 的数据：10 rows
/*!40000 ALTER TABLE `grouping` DISABLE KEYS */;
INSERT INTO `grouping` (`gid`, `groupName`, `description`, `logoPath`, `tag`, `tagid`) VALUES
	('402881e466d21e480166d21e80e00000', '第四级', NULL, NULL, 'fourth', 0),
	('402881e466d21e480166d21e81360001', '社区基金会', NULL, NULL, 'money', 0),
	('402881e466d21e480166d21e813a0002', '未认证', NULL, NULL, 'unreal', 0),
	('402881e466d21e480166d21e81560003', '第二级', NULL, NULL, 'second', 0),
	('402881e466d21e480166d21e81590004', '街道级', NULL, NULL, 'minus_first', 0),
	('402881e466d21e480166d21e815e0005', '第一级', NULL, NULL, 'first', 0),
	('402881e466d21e480166d21e81b30006', '普通认证用户', NULL, NULL, 'common', 0),
	('402881e466d21e480166d21e81c70007', '第三级', NULL, NULL, 'third', 0),
	('402881e466d21e480166d21e81e30008', '社区级', NULL, NULL, 'zero', 0),
	('402881e466d21e480166d21e81eb0009', '系统管理员【系统最高权限】', NULL, NULL, 'admin', 0);
/*!40000 ALTER TABLE `grouping` ENABLE KEYS */;

-- 正在导出表  weixin.house 的数据：5 rows
/*!40000 ALTER TABLE `house` DISABLE KEYS */;
INSERT INTO `house` (`hid`, `name`, `description`, `longitude`, `latitude`, `radus`, `enable`, `zid`, `index4zerolevel`) VALUES
	('402881e4675310a00167538cc3b80012', '核桃园第一活动室', '核桃园第一活动室', 116.46901, 39.92203, 100, b'1', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 0),
	('402881e4677311910167731c44040000', '关东店第一活动室', '123', -1, -1, 0, b'1', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e', 0),
	('402881e468a1b12d0168a1c25d1b000a', '活动室1', '活动室1', 116.4272, 39.92521, 100, b'1', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb', 0),
	('402881e76a00c465016a00d38edd0007', '边府第一活动室', '边府第一活动室', -1, -1, 0, b'1', 'e4b6911e-cbb1-462f-8a2b-9682d360c026', 0),
	('402881e86ab4f517016ab4fc3feb0000', '活动室1', '活动室1', 116.46892, 39.922104, 50, b'1', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68', 0);
/*!40000 ALTER TABLE `house` ENABLE KEYS */;

-- 正在导出表  weixin.manager 的数据：13 rows
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` (`managerid`, `memberid`, `mflid`, `zid`, `flid`, `scid`, `thid`, `foid`, `index4member`, `index4first`, `index4fourth`, `index4minus`, `index4second`, `index4third`, `index4zero`) VALUES
	('11', '7', NULL, NULL, NULL, NULL, '64c60390-8018-4e24-a064-5c18657a67da', NULL, 0, NULL, NULL, NULL, NULL, 0, NULL),
	('12', '8', NULL, NULL, NULL, NULL, NULL, 'abb8c3b7-ad77-4730-aedb-1475dcaa0772', 0, NULL, 0, NULL, NULL, NULL, NULL),
	('6', '3', NULL, 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 1),
	('8', '5', NULL, NULL, 'a2828df0-7155-4005-afa3-7f694b75a1af', NULL, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL),
	('9', '6', NULL, NULL, NULL, '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', NULL, NULL, 0, NULL, NULL, NULL, 0, NULL, NULL),
	('10', '6', NULL, NULL, NULL, '60ae939f-b6cb-4685-ab63-f2bd0b3246b1', NULL, NULL, 1, NULL, NULL, NULL, 0, NULL, NULL),
	('402881e46922f4ab0169233522680003', '402881e46922f4ab01692334c54b0002', NULL, 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0),
	('402881e46922f4ab0169233418e60001', '402881e46922f4ab016923332f160000', '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, NULL),
	('402881e468a1b12d0168a1bd16a70005', '402881e468a1b12d0168a1bcc2bf0004', 'd5718f48-2387-46c3-8c48-78d520540fd3', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, NULL),
	('402881e769db46d10169db4b3bf90007', '402881e769db46d10169db4afd860006', 'fd86918c-d0df-4edb-bf15-e808144b48cc', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, NULL),
	('402881e76a00c465016a00cf9d920001', '402881e76a00c465016a00cf65840000', NULL, 'e4b6911e-cbb1-462f-8a2b-9682d360c026', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0),
	('402881e76a42c6e5016a42daeec90006', '402881e76a42c6e5016a42da4d9c0005', NULL, 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 1),
	('402881e86ab44be1016ab45bad760004', '402881e86ab44be1016ab45a83450003', NULL, 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;

-- 正在导出表  weixin.member 的数据：23 rows
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` (`memberid`, `mflid`, `zid`, `flid`, `scid`, `thid`, `foid`, `uid`, `gid`, `index4user`) VALUES
	('1', NULL, NULL, NULL, NULL, NULL, NULL, '8bec9fcd-286a-4c86-be23-d7ff633901f1', '402881e466d21e480166d21e81590004', 0),
	('2', '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, '945241a9-1710-40fe-8c99-6da4fd259f2c', '402881e466d21e480166d21e81e30008', 0),
	('3', '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, 'e005253b-86ed-4d9b-b00a-4b704c4fc1c0', '402881e466d21e480166d21e81e30008', 0),
	('5', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL, NULL, 'f2a01141-9bff-46ab-9b38-2f3ea9c1b67e', '402881e466d21e480166d21e815e0005', 0),
	('6', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', NULL, NULL, NULL, '1b20f915-62c2-46db-af72-9620464f9a2b', '402881e466d21e480166d21e81560003', 0),
	('7', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', NULL, NULL, '249ffa82-ee75-4c0e-b5bc-32a4870954f6', '402881e466d21e480166d21e81c70007', 0),
	('8', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', 'a2828df0-7155-4005-afa3-7f694b75a1af', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', '64c60390-8018-4e24-a064-5c18657a67da', NULL, '449b89df-25ab-4f4f-b381-19a41a970bc7', '402881e466d21e480166d21e80e00000', 0),
	('9', NULL, NULL, NULL, NULL, NULL, NULL, 'a0083e31-141a-4e12-b315-7621dc1017c6', '402881e466d21e480166d21e81590004', 0),
	('402881e4680caf9b01680cb74e7c0000', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL, NULL, '5ca974f3-e17f-4e46-ab0e-4a27a3645169', '402881e466d21e480166d21e815e0005', 0),
	('402881e468a1b12d0168a1bcc2bf0004', NULL, NULL, NULL, NULL, NULL, NULL, '1681be4f-91b2-4bc1-8f8d-6da2afdc3d22', '402881e466d21e480166d21e81590004', 0),
	('402881e46922f4ab016923332f160000', NULL, NULL, NULL, NULL, NULL, NULL, '7d6c9287-12ae-4626-846e-a1acdcec3b93', '402881e466d21e480166d21e81590004', 0),
	('402881e46922f4ab01692334c54b0002', '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, '87dab0c5-1d1c-47bf-9124-65cb609a5823', '402881e466d21e480166d21e81e30008', 0),
	('402881e4689d05480168a28b17910000', NULL, NULL, NULL, NULL, NULL, NULL, '67c31888-3ff8-4f6d-aa74-b333b8cdcfaa', '402881e466d21e480166d21e81b30006', 0),
	('402881e4689d05480168a28b1a800001', NULL, NULL, NULL, NULL, NULL, NULL, '87653c4c-0139-4716-a933-124ee0c137d1', '402881e466d21e480166d21e81b30006', 0),
	('402881e4689d05480168a2905b320004', 'd5718f48-2387-46c3-8c48-78d520540fd3', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb', NULL, NULL, NULL, NULL, '87653c4c-0139-4716-a933-124ee0c137d1', '402881e466d21e480166d21e81b30006', 1),
	('402881e769db46d10169db4afd860006', NULL, NULL, NULL, NULL, NULL, NULL, '478e1049-d3df-47f9-9ffa-142fe8223494', '402881e466d21e480166d21e81590004', 0),
	('402881e76a00c465016a00cf65840000', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', NULL, NULL, NULL, NULL, NULL, '3d685e77-1301-40af-bfd0-481673234565', '402881e466d21e480166d21e81e30008', 0),
	('402881e76a42c6e5016a42da4d9c0005', '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, 'a0083e31-141a-4e12-b315-7621dc1017c6', '402881e466d21e480166d21e81e30008', 1),
	('402881e86ab44be1016ab457fbfe0000', NULL, NULL, NULL, NULL, NULL, NULL, '3b56fdda-b916-4613-9b05-509c8c43dc18', '402881e466d21e480166d21e81b30006', 0),
	('402881e86ab44be1016ab45a83450003', '47944819-f5ce-41cb-96da-a33ad8dc0284', NULL, NULL, NULL, NULL, NULL, '3b56fdda-b916-4613-9b05-509c8c43dc18', '402881e466d21e480166d21e81e30008', 1),
	('402881e86ab4f517016ab4ffae750001', NULL, NULL, NULL, NULL, NULL, NULL, '47bf2baa-c268-4309-9dff-043c18d45923', '402881e466d21e480166d21e81b30006', 0),
	('402881e86ab4f517016ab501ef4c0004', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68', NULL, NULL, NULL, NULL, '47bf2baa-c268-4309-9dff-043c18d45923', '402881e466d21e480166d21e81b30006', 1),
	('402881e86ad92f4c016ad932c1ff0002', '47944819-f5ce-41cb-96da-a33ad8dc0284', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4', NULL, NULL, NULL, NULL, 'a0083e31-141a-4e12-b315-7621dc1017c6', '402881e466d21e480166d21e81b30006', 2);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;

-- 正在导出表  weixin.minusfirstlevel 的数据：4 rows
/*!40000 ALTER TABLE `minusfirstlevel` DISABLE KEYS */;
INSERT INTO `minusfirstlevel` (`mflid`, `name`, `description`, `level`, `qrcode`, `qrcodeTime`) VALUES
	('47944819-f5ce-41cb-96da-a33ad8dc0284', '呼家楼', '呼家楼街道', -1, 'qrcode\\10\\14\\tag=minus_first&lid=47944819-f5ce-41cb-96da-a33ad8dc0284.gif', 1543287956185),
	('d5718f48-2387-46c3-8c48-78d520540fd3', '朝阳门', '朝阳门街道办事处', -1, 'qrcode\\2\\7\\tag=minus_first&lid=d5718f48-2387-46c3-8c48-78d520540fd3.gif', 1548901178232),
	('fd86918c-d0df-4edb-bf15-e808144b48cc', '老山街道', '老山街道', -1, 'qrcode\\6\\12\\tag=minus_first&lid=fd86918c-d0df-4edb-bf15-e808144b48cc.gif', 1554162161954),
	('2c1c8666-b43f-44b9-90ca-ac5c2de4c80a', '苹果园街道', '苹果园街道', -1, 'qrcode\\3\\1\\tag=minus_first&lid=2c1c8666-b43f-44b9-90ca-ac5c2de4c80a.gif', 1554162175023);
/*!40000 ALTER TABLE `minusfirstlevel` ENABLE KEYS */;

-- 正在导出表  weixin.permission 的数据：127 rows
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` (`pid`, `permissionName`, `description`, `enabled`, `ptid`) VALUES
	('402881e467101c830167102899050002', 'retrieve', '查询', 'T', '402881e467101c830167102899050001'),
	('402881e467101c830167102899060003', 'create', '新建', 'T', '402881e467101c830167102899050001'),
	('402881e467101c830167102899060004', 'update', '修改', 'T', '402881e467101c830167102899050001'),
	('402881e467101c830167102899060005', 'delete', '删除', 'T', '402881e467101c830167102899050001'),
	('402881e467101c830167102899060007', 'retrieve', '查询', 'T', '402881e467101c830167102899060006'),
	('402881e467101c830167102899060008', 'create', '新建', 'T', '402881e467101c830167102899060006'),
	('402881e467101c830167102899180009', 'update', '修改', 'T', '402881e467101c830167102899060006'),
	('402881e467101c83016710289918000a', 'delete', '删除', 'T', '402881e467101c830167102899060006'),
	('402881e467101c83016710289918000c', 'retrieve', '查询', 'T', '402881e467101c83016710289918000b'),
	('402881e467101c83016710289918000d', 'create', '新建', 'T', '402881e467101c83016710289918000b'),
	('402881e467101c83016710289918000e', 'update', '修改', 'T', '402881e467101c83016710289918000b'),
	('402881e467101c83016710289918000f', 'delete', '删除', 'T', '402881e467101c83016710289918000b'),
	('402881e467101c830167102899190011', 'retrieve', '查询', 'T', '402881e467101c830167102899190010'),
	('402881e467101c830167102899190012', 'create', '新建', 'T', '402881e467101c830167102899190010'),
	('402881e467101c830167102899190013', 'update', '修改', 'T', '402881e467101c830167102899190010'),
	('402881e467101c830167102899190014', 'delete', '删除', 'T', '402881e467101c830167102899190010'),
	('402881e467101c830167102899190016', 'retrieveGeo', '查询地点', 'T', '402881e467101c830167102899190015'),
	('402881e467101c8301671028991a0017', 'createGeo', '新建地点', 'T', '402881e467101c830167102899190015'),
	('402881e467101c8301671028991a0018', 'updateGeo', '更新地点', 'T', '402881e467101c830167102899190015'),
	('402881e467101c8301671028991a0019', 'deleteGeo', '删除地点', 'T', '402881e467101c830167102899190015'),
	('402881e467101c83016710289a4e001c', 'retrieve', '查询', 'T', '402881e467101c83016710289a4e001b'),
	('402881e467101c83016710289a4e001d', 'create', '新建', 'T', '402881e467101c83016710289a4e001b'),
	('402881e467101c83016710289a4e001e', 'update', '修改', 'T', '402881e467101c83016710289a4e001b'),
	('402881e467101c83016710289a4e001f', 'delete', '删除', 'T', '402881e467101c83016710289a4e001b'),
	('402881e467101c83016710289a500021', 'retrieveHouse', '查询房屋', 'T', '402881e467101c83016710289a4e0020'),
	('402881e467101c83016710289a500022', 'createHouse', '新建房屋', 'T', '402881e467101c83016710289a4e0020'),
	('402881e467101c83016710289a500023', 'updateHouse', '修改房屋', 'T', '402881e467101c83016710289a4e0020'),
	('402881e467101c83016710289a500024', 'deleteHouse', '删除房屋', 'T', '402881e467101c83016710289a4e0020'),
	('402881e467101c83016710289a500025', 'retrieveActivity', '查询房屋活动', 'T', '402881e467101c83016710289a4e0020'),
	('402881e467101c83016710289a500027', 'retrieve', '查询', 'T', '402881e467101c83016710289a500026'),
	('402881e467101c83016710289a500028', 'create', '新建', 'T', '402881e467101c83016710289a500026'),
	('402881e467101c83016710289a510029', 'update', '修改', 'T', '402881e467101c83016710289a500026'),
	('402881e467101c83016710289a51002a', 'delete', '删除', 'T', '402881e467101c83016710289a500026'),
	('402881e467101c83016710289a52002c', 'retrieve', '查询', 'T', '402881e467101c83016710289a52002b'),
	('402881e467101c83016710289a52002d', 'create', '新建', 'T', '402881e467101c83016710289a52002b'),
	('402881e467101c83016710289a52002e', 'update', '修改', 'T', '402881e467101c83016710289a52002b'),
	('402881e467101c83016710289a52002f', 'delete', '删除', 'T', '402881e467101c83016710289a52002b'),
	('402881e467101c83016710289a540031', 'retrieve', '查询', 'T', '402881e467101c83016710289a530030'),
	('402881e467101c83016710289a540032', 'create', '新建', 'T', '402881e467101c83016710289a530030'),
	('402881e467101c83016710289a540033', 'update', '修改', 'T', '402881e467101c83016710289a530030'),
	('402881e467101c83016710289a540034', 'delete', '删除', 'T', '402881e467101c83016710289a530030'),
	('402881e467101c83016710289a540036', 'retrieveGeo', '查询地点', 'T', '402881e467101c83016710289a540035'),
	('402881e467101c83016710289a540037', 'createGeo', '新建地点', 'T', '402881e467101c83016710289a540035'),
	('402881e467101c83016710289a540038', 'updateGeo', '更新地点', 'T', '402881e467101c83016710289a540035'),
	('402881e467101c83016710289a540039', 'deleteGeo', '删除地点', 'T', '402881e467101c83016710289a540035'),
	('402881e467101c83016710289aa6003c', 'retrieve', '查询', 'T', '402881e467101c83016710289aa5003b'),
	('402881e467101c83016710289aa6003d', 'create', '新建', 'T', '402881e467101c83016710289aa5003b'),
	('402881e467101c83016710289aa6003e', 'update', '修改', 'T', '402881e467101c83016710289aa5003b'),
	('402881e467101c83016710289aa6003f', 'delete', '删除', 'T', '402881e467101c83016710289aa5003b'),
	('402881e467101c83016710289aa70041', 'retrieve', '查询', 'T', '402881e467101c83016710289aa70040'),
	('402881e467101c83016710289aa70042', 'create', '新建', 'T', '402881e467101c83016710289aa70040'),
	('402881e467101c83016710289aa70043', 'update', '修改', 'T', '402881e467101c83016710289aa70040'),
	('402881e467101c83016710289aa70044', 'delete', '删除', 'T', '402881e467101c83016710289aa70040'),
	('402881e467101c83016710289aa70046', 'retrieve', '查询', 'T', '402881e467101c83016710289aa70045'),
	('402881e467101c83016710289aa70047', 'create', '新建', 'T', '402881e467101c83016710289aa70045'),
	('402881e467101c83016710289aa70048', 'update', '修改', 'T', '402881e467101c83016710289aa70045'),
	('402881e467101c83016710289aa70049', 'delete', '删除', 'T', '402881e467101c83016710289aa70045'),
	('402881e467101c83016710289aa8004b', 'retrieve', '查询', 'T', '402881e467101c83016710289aa7004a'),
	('402881e467101c83016710289aa8004c', 'create', '新建', 'T', '402881e467101c83016710289aa7004a'),
	('402881e467101c83016710289aa8004d', 'update', '修改', 'T', '402881e467101c83016710289aa7004a'),
	('402881e467101c83016710289aa8004e', 'delete', '删除', 'T', '402881e467101c83016710289aa7004a'),
	('402881e467101c83016710289aaa0050', 'retrieveGeo', '查询地点', 'T', '402881e467101c83016710289aa8004f'),
	('402881e467101c83016710289aaa0051', 'createGeo', '新建地点', 'T', '402881e467101c83016710289aa8004f'),
	('402881e467101c83016710289aaa0052', 'updateGeo', '更新地点', 'T', '402881e467101c83016710289aa8004f'),
	('402881e467101c83016710289aaa0053', 'deleteGeo', '删除地点', 'T', '402881e467101c83016710289aa8004f'),
	('402881e467101c83016710289acd0056', 'retrieve', '查询', 'T', '402881e467101c83016710289acc0055'),
	('402881e467101c83016710289acd0057', 'create', '新建', 'T', '402881e467101c83016710289acc0055'),
	('402881e467101c83016710289acd0058', 'update', '修改', 'T', '402881e467101c83016710289acc0055'),
	('402881e467101c83016710289acd0059', 'delete', '删除', 'T', '402881e467101c83016710289acc0055'),
	('402881e467101c83016710289acd005b', 'retrieve', '查询', 'T', '402881e467101c83016710289acd005a'),
	('402881e467101c83016710289acd005c', 'create', '新建', 'T', '402881e467101c83016710289acd005a'),
	('402881e467101c83016710289acd005d', 'update', '修改', 'T', '402881e467101c83016710289acd005a'),
	('402881e467101c83016710289acd005e', 'delete', '删除', 'T', '402881e467101c83016710289acd005a'),
	('402881e467101c83016710289ace0060', 'retrieve', '查询', 'T', '402881e467101c83016710289acd005f'),
	('402881e467101c83016710289ace0061', 'create', '新建', 'T', '402881e467101c83016710289acd005f'),
	('402881e467101c83016710289ace0062', 'update', '修改', 'T', '402881e467101c83016710289acd005f'),
	('402881e467101c83016710289ace0063', 'delete', '删除', 'T', '402881e467101c83016710289acd005f'),
	('402881e467101c83016710289ace0065', 'retrieve', '查询', 'T', '402881e467101c83016710289ace0064'),
	('402881e467101c83016710289ace0066', 'create', '新建', 'T', '402881e467101c83016710289ace0064'),
	('402881e467101c83016710289ace0067', 'update', '修改', 'T', '402881e467101c83016710289ace0064'),
	('402881e467101c83016710289ace0068', 'delete', '删除', 'T', '402881e467101c83016710289ace0064'),
	('402881e467101c83016710289ace006a', 'retrieveGeo', '查询地点', 'T', '402881e467101c83016710289ace0069'),
	('402881e467101c83016710289ace006b', 'createGeo', '新建地点', 'T', '402881e467101c83016710289ace0069'),
	('402881e467101c83016710289acf006c', 'updateGeo', '更新地点', 'T', '402881e467101c83016710289ace0069'),
	('402881e467101c83016710289acf006d', 'deleteGeo', '删除地点', 'T', '402881e467101c83016710289ace0069'),
	('402881e467101c83016710289b010070', 'retrieve', '查询', 'T', '402881e467101c83016710289b00006f'),
	('402881e467101c83016710289b020071', 'create', '新建', 'T', '402881e467101c83016710289b00006f'),
	('402881e467101c83016710289b020072', 'update', '修改', 'T', '402881e467101c83016710289b00006f'),
	('402881e467101c83016710289b020073', 'delete', '删除', 'T', '402881e467101c83016710289b00006f'),
	('402881e467101c83016710289b020075', 'retrieve', '查询', 'T', '402881e467101c83016710289b020074'),
	('402881e467101c83016710289b020076', 'create', '新建', 'T', '402881e467101c83016710289b020074'),
	('402881e467101c83016710289b020077', 'update', '修改', 'T', '402881e467101c83016710289b020074'),
	('402881e467101c83016710289b020078', 'delete', '删除', 'T', '402881e467101c83016710289b020074'),
	('402881e467101c83016710289b02007a', 'retrieve', '查询', 'T', '402881e467101c83016710289b020079'),
	('402881e467101c83016710289b02007b', 'create', '新建', 'T', '402881e467101c83016710289b020079'),
	('402881e467101c83016710289b03007c', 'update', '修改', 'T', '402881e467101c83016710289b020079'),
	('402881e467101c83016710289b03007d', 'delete', '删除', 'T', '402881e467101c83016710289b020079'),
	('402881e467101c83016710289b03007f', 'retrieve', '查询', 'T', '402881e467101c83016710289b03007e'),
	('402881e467101c83016710289b040080', 'create', '新建', 'T', '402881e467101c83016710289b03007e'),
	('402881e467101c83016710289b040081', 'update', '修改', 'T', '402881e467101c83016710289b03007e'),
	('402881e467101c83016710289b040082', 'delete', '删除', 'T', '402881e467101c83016710289b03007e'),
	('402881e467101c83016710289b040084', 'retrieveGeo', '查询地点', 'T', '402881e467101c83016710289b040083'),
	('402881e467101c83016710289b040085', 'createGeo', '新建地点', 'T', '402881e467101c83016710289b040083'),
	('402881e467101c83016710289b040086', 'updateGeo', '更新地点', 'T', '402881e467101c83016710289b040083'),
	('402881e467101c83016710289b050087', 'deleteGeo', '删除地点', 'T', '402881e467101c83016710289b040083'),
	('402881e467101c83016710289b41008a', 'retrieve', '查询', 'T', '402881e467101c83016710289b400089'),
	('402881e467101c83016710289b41008b', 'create', '新建', 'T', '402881e467101c83016710289b400089'),
	('402881e467101c83016710289b41008c', 'update', '修改', 'T', '402881e467101c83016710289b400089'),
	('402881e467101c83016710289b41008d', 'delete', '删除', 'T', '402881e467101c83016710289b400089'),
	('402881e467101c83016710289b41008f', 'retrieve', '查询', 'T', '402881e467101c83016710289b41008e'),
	('402881e467101c83016710289b410090', 'create', '新建', 'T', '402881e467101c83016710289b41008e'),
	('402881e467101c83016710289b410091', 'update', '修改', 'T', '402881e467101c83016710289b41008e'),
	('402881e467101c83016710289b410092', 'delete', '删除', 'T', '402881e467101c83016710289b41008e'),
	('402881e467101c83016710289b410094', 'retrieve', '查询', 'T', '402881e467101c83016710289b410093'),
	('402881e467101c83016710289b410095', 'create', '新建', 'T', '402881e467101c83016710289b410093'),
	('402881e467101c83016710289b410096', 'update', '修改', 'T', '402881e467101c83016710289b410093'),
	('402881e467101c83016710289b410097', 'delete', '删除', 'T', '402881e467101c83016710289b410093'),
	('402881e467101c83016710289b420099', 'retrieve', '查询', 'T', '402881e467101c83016710289b420098'),
	('402881e467101c83016710289b42009a', 'create', '新建', 'T', '402881e467101c83016710289b420098'),
	('402881e467101c83016710289b42009b', 'update', '修改', 'T', '402881e467101c83016710289b420098'),
	('402881e467101c83016710289b42009c', 'delete', '删除', 'T', '402881e467101c83016710289b420098'),
	('402881e467101c83016710289b43009e', 'retrieveGeo', '查询地点', 'T', '402881e467101c83016710289b43009d'),
	('402881e467101c83016710289b43009f', 'createGeo', '新建地点', 'T', '402881e467101c83016710289b43009d'),
	('402881e467101c83016710289b4300a0', 'updateGeo', '更新地点', 'T', '402881e467101c83016710289b43009d'),
	('402881e467101c83016710289b4300a1', 'deleteGeo', '删除地点', 'T', '402881e467101c83016710289b43009d'),
	('402881e467101c83016710289b6600a4', 'access4desk', '桌面端访问', 'T', '402881e467101c83016710289b6600a3'),
	('402881e467101c83016710289b6600a5', 'access4weixin', '微信端访问', 'T', '402881e467101c83016710289b6600a3');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;

-- 正在导出表  weixin.permissionlevel 的数据：7 rows
/*!40000 ALTER TABLE `permissionlevel` DISABLE KEYS */;
INSERT INTO `permissionlevel` (`plid`, `permissionLevelName`, `description`, `enabled`, `level`) VALUES
	('402881e467101c830167102898c80000', 'minus_first', '街道层级', 'T', -1),
	('402881e467101c83016710289a4d001a', 'zero', '社区层级', 'T', 0),
	('402881e467101c83016710289aa5003a', 'first', '第一层级', 'T', 1),
	('402881e467101c83016710289acc0054', 'second', '第二层级', 'T', 2),
	('402881e467101c83016710289b00006e', 'third', '第三层级', 'T', 3),
	('402881e467101c83016710289b400088', 'fourth', '第四层级', 'T', 4),
	('402881e467101c83016710289b6600a2', 'all', '通用权限', 'T', 10086);
/*!40000 ALTER TABLE `permissionlevel` ENABLE KEYS */;

-- 正在导出表  weixin.permissiontype 的数据：32 rows
/*!40000 ALTER TABLE `permissiontype` DISABLE KEYS */;
INSERT INTO `permissiontype` (`ptid`, `permissionTypeName`, `description`, `enabled`, `plid`) VALUES
	('402881e467101c830167102899050001', 'activity', '活动类型', 'T', '402881e467101c830167102898c80000'),
	('402881e467101c830167102899060006', 'level', '层级类型', 'T', '402881e467101c830167102898c80000'),
	('402881e467101c83016710289918000b', 'user', '人员类型', 'T', '402881e467101c830167102898c80000'),
	('402881e467101c830167102899190010', 'project', '项目类型', 'T', '402881e467101c830167102898c80000'),
	('402881e467101c830167102899190015', 'geo', '地点类型', 'T', '402881e467101c830167102898c80000'),
	('402881e467101c83016710289a4e001b', 'activity', '活动类型', 'T', '402881e467101c83016710289a4d001a'),
	('402881e467101c83016710289a4e0020', 'house', '房屋类型', 'T', '402881e467101c83016710289a4d001a'),
	('402881e467101c83016710289a500026', 'level', '层级类型', 'T', '402881e467101c83016710289a4d001a'),
	('402881e467101c83016710289a52002b', 'user', '人员类型', 'T', '402881e467101c83016710289a4d001a'),
	('402881e467101c83016710289a530030', 'project', '项目类型', 'T', '402881e467101c83016710289a4d001a'),
	('402881e467101c83016710289a540035', 'geo', '地点类型', 'T', '402881e467101c83016710289a4d001a'),
	('402881e467101c83016710289aa5003b', 'activity', '活动类型', 'T', '402881e467101c83016710289aa5003a'),
	('402881e467101c83016710289aa70040', 'level', '层级类型', 'T', '402881e467101c83016710289aa5003a'),
	('402881e467101c83016710289aa70045', 'user', '人员类型', 'T', '402881e467101c83016710289aa5003a'),
	('402881e467101c83016710289aa7004a', 'project', '项目类型', 'T', '402881e467101c83016710289aa5003a'),
	('402881e467101c83016710289aa8004f', 'geo', '地点类型', 'T', '402881e467101c83016710289aa5003a'),
	('402881e467101c83016710289acc0055', 'activity', '活动类型', 'T', '402881e467101c83016710289acc0054'),
	('402881e467101c83016710289acd005a', 'level', '层级类型', 'T', '402881e467101c83016710289acc0054'),
	('402881e467101c83016710289acd005f', 'user', '人员类型', 'T', '402881e467101c83016710289acc0054'),
	('402881e467101c83016710289ace0064', 'project', '项目类型', 'T', '402881e467101c83016710289acc0054'),
	('402881e467101c83016710289ace0069', 'geo', '地点类型', 'T', '402881e467101c83016710289acc0054'),
	('402881e467101c83016710289b00006f', 'activity', '活动类型', 'T', '402881e467101c83016710289b00006e'),
	('402881e467101c83016710289b020074', 'level', '层级类型', 'T', '402881e467101c83016710289b00006e'),
	('402881e467101c83016710289b020079', 'user', '人员类型', 'T', '402881e467101c83016710289b00006e'),
	('402881e467101c83016710289b03007e', 'project', '项目类型', 'T', '402881e467101c83016710289b00006e'),
	('402881e467101c83016710289b040083', 'geo', '地点类型', 'T', '402881e467101c83016710289b00006e'),
	('402881e467101c83016710289b400089', 'activity', '活动类型', 'T', '402881e467101c83016710289b400088'),
	('402881e467101c83016710289b41008e', 'level', '层级类型', 'T', '402881e467101c83016710289b400088'),
	('402881e467101c83016710289b410093', 'user', '人员类型', 'T', '402881e467101c83016710289b400088'),
	('402881e467101c83016710289b420098', 'project', '项目类型', 'T', '402881e467101c83016710289b400088'),
	('402881e467101c83016710289b43009d', 'geo', '地点类型', 'T', '402881e467101c83016710289b400088'),
	('402881e467101c83016710289b6600a3', 'system', '系统后台', 'T', '402881e467101c83016710289b6600a2');
/*!40000 ALTER TABLE `permissiontype` ENABLE KEYS */;

-- 正在导出表  weixin.permission_firstlevel 的数据：44 rows
/*!40000 ALTER TABLE `permission_firstlevel` DISABLE KEYS */;
INSERT INTO `permission_firstlevel` (`pid`, `flid`) VALUES
	('402881e467101c83016710289aa6003c', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa6003d', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa6003e', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa6003f', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70041', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70042', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70043', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70044', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70046', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70047', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70048', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa70049', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa8004b', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa8004c', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa8004d', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa8004e', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aaa0050', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aaa0051', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aaa0052', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aaa0053', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289b6600a4', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289b6600a5', 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('402881e467101c83016710289aa6003c', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa6003d', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa6003e', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa6003f', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70041', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70042', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70043', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70044', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70046', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70047', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70048', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa70049', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa8004b', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa8004c', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa8004d', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aa8004e', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aaa0050', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aaa0051', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aaa0052', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289aaa0053', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289b6600a4', 'f6e43211-473d-4246-8281-e0fca8b6a7c7'),
	('402881e467101c83016710289b6600a5', 'f6e43211-473d-4246-8281-e0fca8b6a7c7');
/*!40000 ALTER TABLE `permission_firstlevel` ENABLE KEYS */;

-- 正在导出表  weixin.permission_fourthlevel 的数据：22 rows
/*!40000 ALTER TABLE `permission_fourthlevel` DISABLE KEYS */;
INSERT INTO `permission_fourthlevel` (`pid`, `foid`) VALUES
	('402881e467101c83016710289b41008a', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b41008b', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b41008c', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b41008d', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b41008f', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b410090', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b410091', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b410092', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b410094', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b410095', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b410096', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b410097', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b420099', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b42009a', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b42009b', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b42009c', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b43009e', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b43009f', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b4300a0', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b4300a1', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b6600a4', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772'),
	('402881e467101c83016710289b6600a5', 'abb8c3b7-ad77-4730-aedb-1475dcaa0772');
/*!40000 ALTER TABLE `permission_fourthlevel` ENABLE KEYS */;

-- 正在导出表  weixin.permission_minusfirstlevel 的数据：88 rows
/*!40000 ALTER TABLE `permission_minusfirstlevel` DISABLE KEYS */;
INSERT INTO `permission_minusfirstlevel` (`pid`, `mflid`) VALUES
	('402881e467101c830167102899050002', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899060003', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899060004', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899060005', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899060007', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899060008', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899180009', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c83016710289918000a', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c83016710289918000c', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c83016710289918000d', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c83016710289918000e', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c83016710289918000f', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899190011', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899190012', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899190013', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899190014', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899190016', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c8301671028991a0017', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c8301671028991a0018', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c8301671028991a0019', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c83016710289b6600a4', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c83016710289b6600a5', '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a'),
	('402881e467101c830167102899050002', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899060003', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899060004', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899060005', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899060007', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899060008', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899180009', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c83016710289918000a', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c83016710289918000c', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c83016710289918000d', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c83016710289918000e', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c83016710289918000f', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899190011', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899190012', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899190013', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899190014', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899190016', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c8301671028991a0017', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c8301671028991a0018', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c8301671028991a0019', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c83016710289b6600a4', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c83016710289b6600a5', '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('402881e467101c830167102899050002', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899060003', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899060004', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899060005', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899060007', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899060008', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899180009', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c83016710289918000a', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c83016710289918000c', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c83016710289918000d', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c83016710289918000e', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c83016710289918000f', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899190011', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899190012', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899190013', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899190014', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899190016', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c8301671028991a0017', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c8301671028991a0018', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c8301671028991a0019', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c83016710289b6600a4', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c83016710289b6600a5', 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('402881e467101c830167102899050002', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899060003', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899060004', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899060005', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899060007', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899060008', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899180009', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c83016710289918000a', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c83016710289918000c', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c83016710289918000d', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c83016710289918000e', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c83016710289918000f', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899190011', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899190012', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899190013', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899190014', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c830167102899190016', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c8301671028991a0017', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c8301671028991a0018', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c8301671028991a0019', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c83016710289b6600a4', 'fd86918c-d0df-4edb-bf15-e808144b48cc'),
	('402881e467101c83016710289b6600a5', 'fd86918c-d0df-4edb-bf15-e808144b48cc');
/*!40000 ALTER TABLE `permission_minusfirstlevel` ENABLE KEYS */;

-- 正在导出表  weixin.permission_secondlevel 的数据：44 rows
/*!40000 ALTER TABLE `permission_secondlevel` DISABLE KEYS */;
INSERT INTO `permission_secondlevel` (`pid`, `scid`) VALUES
	('402881e467101c83016710289acd0056', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd0057', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd0058', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd0059', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd005b', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd005c', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd005d', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd005e', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0060', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0061', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0062', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0063', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0065', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0066', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0067', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace0068', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace006a', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289ace006b', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acf006c', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acf006d', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289b6600a4', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289b6600a5', '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('402881e467101c83016710289acd0056', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acd0057', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acd0058', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acd0059', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acd005b', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acd005c', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acd005d', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acd005e', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0060', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0061', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0062', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0063', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0065', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0066', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0067', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace0068', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace006a', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289ace006b', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acf006c', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289acf006d', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289b6600a4', 'bca0fb80-843d-4b95-9d51-0283116cdb20'),
	('402881e467101c83016710289b6600a5', 'bca0fb80-843d-4b95-9d51-0283116cdb20');
/*!40000 ALTER TABLE `permission_secondlevel` ENABLE KEYS */;

-- 正在导出表  weixin.permission_thirdlevel 的数据：22 rows
/*!40000 ALTER TABLE `permission_thirdlevel` DISABLE KEYS */;
INSERT INTO `permission_thirdlevel` (`pid`, `thid`) VALUES
	('402881e467101c83016710289b010070', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b020071', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b020072', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b020073', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b020075', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b020076', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b020077', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b020078', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b02007a', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b02007b', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b03007c', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b03007d', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b03007f', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b040080', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b040081', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b040082', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b040084', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b040085', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b040086', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b050087', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b6600a4', '64c60390-8018-4e24-a064-5c18657a67da'),
	('402881e467101c83016710289b6600a5', '64c60390-8018-4e24-a064-5c18657a67da');
/*!40000 ALTER TABLE `permission_thirdlevel` ENABLE KEYS */;

-- 正在导出表  weixin.permission_zerolevel 的数据：135 rows
/*!40000 ALTER TABLE `permission_zerolevel` DISABLE KEYS */;
INSERT INTO `permission_zerolevel` (`pid`, `zid`) VALUES
	('402881e467101c83016710289a4e001c', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a4e001d', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a4e001e', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a4e001f', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a500021', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a500022', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a500023', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a500024', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a500025', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a500027', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a500028', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a510029', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a51002a', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a52002c', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a52002d', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a52002e', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a52002f', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540031', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540032', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540033', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540034', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540036', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540037', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540038', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a540039', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289b6600a4', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289b6600a5', 'd16322ee-9d3f-418a-8cc9-bdd027858cbb'),
	('402881e467101c83016710289a4e001c', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a4e001d', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a4e001e', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a4e001f', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a500021', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a500022', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a500023', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a500024', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a500025', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a500027', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a500028', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a510029', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a51002a', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a52002c', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a52002d', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a52002e', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a52002f', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540031', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540032', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540033', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540034', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540036', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540037', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540038', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a540039', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289b6600a4', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289b6600a5', 'd5c460ba-6897-4d54-8b45-1b20e5eb6b68'),
	('402881e467101c83016710289a4e001c', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a4e001d', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a4e001e', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a4e001f', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a500021', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a500022', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a500023', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a500024', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a500025', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a500027', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a500028', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a510029', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a51002a', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a52002c', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a52002d', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a52002e', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a52002f', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540031', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540032', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540033', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540034', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540036', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540037', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540038', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a540039', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289b6600a4', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289b6600a5', 'e4b6911e-cbb1-462f-8a2b-9682d360c026'),
	('402881e467101c83016710289a4e001c', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a4e001d', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a4e001e', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a4e001f', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a500021', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a500022', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a500023', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a500024', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a500025', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a500027', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a500028', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a510029', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a51002a', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a52002c', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a52002d', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a52002e', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a52002f', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540031', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540032', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540033', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540034', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540036', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540037', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540038', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a540039', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289b6600a4', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289b6600a5', 'e9d0a77e-75d3-4f13-ac0a-7ffed167827e'),
	('402881e467101c83016710289a4e001c', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a4e001d', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a4e001e', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a4e001f', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a500021', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a500022', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a500023', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a500024', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a500025', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a500027', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a500028', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a510029', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a51002a', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a52002c', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a52002d', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a52002e', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a52002f', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540031', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540032', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540033', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540034', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540036', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540037', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540038', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289a540039', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289b6600a4', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4'),
	('402881e467101c83016710289b6600a5', 'ebf0f36c-efbf-4584-b579-8ecfb3c365e4');
/*!40000 ALTER TABLE `permission_zerolevel` ENABLE KEYS */;

-- 正在导出表  weixin.projecttype 的数据：4 rows
/*!40000 ALTER TABLE `projecttype` DISABLE KEYS */;
INSERT INTO `projecttype` (`ptid`, `name`, `description`) VALUES
	('402881e466d26ccd0166d26cf4480000', 'old', '为老服务'),
	('402881e466d26ccd0166d26cf4960001', 'volunter', '志愿服务'),
	('402881e466d26ccd0166d26cf4960002', 'common', '普通服务'),
	('402881e466d26ccd0166d26cf4a60003', 'young', '青少年服务');
/*!40000 ALTER TABLE `projecttype` ENABLE KEYS */;

-- 正在导出表  weixin.receipt4besureproject 的数据：0 rows
/*!40000 ALTER TABLE `receipt4besureproject` DISABLE KEYS */;
/*!40000 ALTER TABLE `receipt4besureproject` ENABLE KEYS */;

-- 正在导出表  weixin.reply4userjoinlevelapprove 的数据：14 rows
/*!40000 ALTER TABLE `reply4userjoinlevelapprove` DISABLE KEYS */;
INSERT INTO `reply4userjoinlevelapprove` (`r4ujlaid`, `timeStamp`, `timeStr`, `beread`, `message`, `a4ujlid`, `index4approve`) VALUES
	('402881e4687dce2201687de5b1850000', 1548300430994, '2019-01-24 11:27:10', b'0', '人满为患了', '402881e4687d8da501687da0d9da0000', 0),
	('402881e4687e77cc01687ea800280001', 1548313165860, '2019-01-24 14:59:25', b'0', NULL, '402881e4687e77cc01687ea11e1a0000', 0),
	('402881e4687e77cc01687ebcbb780004', 1548314524505, '2019-01-24 15:22:04', b'0', NULL, '402881e4687e77cc01687eac7d570003', 0),
	('402881e4687e77cc01687ec18bab0006', 1548314839978, '2019-01-24 15:27:19', b'0', '123', '402881e4687e77cc01687ebf7eaf0005', 0),
	('402881e4688e641901688e73c2f50001', 1548578177778, '2019-01-27 16:36:17', b'0', '非常非常不好', '402881e4688e641901688e6f9e150000', 0),
	('402881e468a1b12d0168a1bf77b60007', 1548901906352, '2019-01-31 10:31:46', b'0', '不错，欢迎', '402881e468a1b12d0168a1be82840006', 0),
	('402881e468a1b12d01689c9f5090000c', 1548815913102, '2019-01-30 10:38:33', b'0', '欢迎来到朝阳门', '402881e468a1b12d01689c9edf58000b', 0),
	('402881e4689d05480168a2905b200003', 1548915596035, '2019-01-31 14:19:56', b'0', '同意', '402881e4689d05480168a28fc4e50002', 0),
	('402881e76a00c465016a00d156980003', 1554791880342, '2019-04-09 14:38:00', b'0', '通过', '402881e76a00c465016a00d0c0280002', 0),
	('402881e76a42c6e5016a42d714a70001', 1555899552915, '2019-04-22 10:19:12', b'0', '通过1', '402881e76a42c6e5016a42d694ff0000', 0),
	('402881e76a42c6e5016a42da4d920004', 1555899764113, '2019-04-22 10:22:44', b'0', '111', '402881e76a42c6e5016a42d9fbc00003', 0),
	('402881e86ab44be1016ab45a832b0002', 1557803991849, '2019-05-14 11:19:51', b'0', '111', '402881e86ab44be1016ab45a21dc0001', 0),
	('402881e86ab4f517016ab501ef010003', 1557814963967, '2019-05-14 14:22:43', b'0', '111', '402881e86ab4f517016ab501607b0002', 0),
	('402881e86ad92f4c016ad932c1db0001', 1558422143450, '2019-05-21 15:02:23', b'0', '111', '402881e86ad92f4c016ad9328e8e0000', 0);
/*!40000 ALTER TABLE `reply4userjoinlevelapprove` ENABLE KEYS */;

-- 正在导出表  weixin.secondlevel 的数据：3 rows
/*!40000 ALTER TABLE `secondlevel` DISABLE KEYS */;
INSERT INTO `secondlevel` (`scid`, `name`, `description`, `level`, `qrcode`, `qrcodeTime`, `flid`) VALUES
	('60ae939f-b6cb-4685-ab63-f2bd0b3246b1', '青少年志愿者', '青少年志愿者', 2, 'qrcode\\8\\8\\tag=second&lid=60ae939f-b6cb-4685-ab63-f2bd0b3246b1.gif', 1543295739681, 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('2aff4197-3f14-40fb-8c7e-e5439a3ddd5f', '老年志愿者', '专门从事为老服务的志愿者', 2, 'qrcode\\1\\5\\tag=second&lid=2aff4197-3f14-40fb-8c7e-e5439a3ddd5f.gif', 1543288162410, 'a2828df0-7155-4005-afa3-7f694b75a1af'),
	('bca0fb80-843d-4b95-9d51-0283116cdb20', '为老服务志愿者', '志愿者中专门提供为老服务工作的志愿者类型', 2, 'qrcode\\5\\8\\tag=second&lid=bca0fb80-843d-4b95-9d51-0283116cdb20.gif', 1546496503531, 'a2828df0-7155-4005-afa3-7f694b75a1af');
/*!40000 ALTER TABLE `secondlevel` ENABLE KEYS */;

-- 正在导出表  weixin.thirdlevel 的数据：2 rows
/*!40000 ALTER TABLE `thirdlevel` DISABLE KEYS */;
INSERT INTO `thirdlevel` (`thid`, `name`, `description`, `level`, `qrcode`, `qrcodeTime`, `scid`) VALUES
	('9dfdca3e-60a9-4f5e-b777-69c95656f607', '老李头为老服务队', '老李头为老服务队', 3, 'qrcode\\14\\1\\tag=third&lid=9dfdca3e-60a9-4f5e-b777-69c95656f607.gif', 1543295982854, '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f'),
	('64c60390-8018-4e24-a064-5c18657a67da', '老张头为老服务队', '老张头为老服务队', 3, 'qrcode\\6\\0\\tag=third&lid=64c60390-8018-4e24-a064-5c18657a67da.gif', 1543288255956, '2aff4197-3f14-40fb-8c7e-e5439a3ddd5f');
/*!40000 ALTER TABLE `thirdlevel` ENABLE KEYS */;

-- 正在导出表  weixin.user 的数据：18 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`uid`, `openid`, `cardid`, `username`, `password`, `sickname`, `registrationTime`, `address`, `serveTime`, `email`, `score`, `sex`, `birth`, `age`, `phone`, `qrcode`, `ishere`, `locked`) VALUES
	('a0083e31-141a-4e12-b315-7621dc1017c6', 'okNKU0Vb9EQtWTfteAyS3nVMd0Iw', '', '吉磊', '123', '这一天天的', 1543810896746, '安道尔,,', 0, '', 9, '男', '1980-7-15', 33, '13718805500', 'qrcode\\6\\2\\a0083e31-141a-4e12-b315-7621dc1017c6.gif', 'T', 'F'),
	('449b89df-25ab-4f4f-b381-19a41a970bc7', NULL, '123', '第一支队长', '123', '123', 1543296275529, '123', 0, '123', 1, '女', NULL, 123, '123', 'qrcode\\1\\7\\449b89df-25ab-4f4f-b381-19a41a970bc7.gif', 'F', 'F'),
	('1b20f915-62c2-46db-af72-9620464f9a2b', NULL, '123123123', '志愿者大队长', '123', 'zyzddz', 1543295616388, '123', 0, '123', 2, '男', NULL, 123, '123', 'qrcode\\2\\0\\1b20f915-62c2-46db-af72-9620464f9a2b.gif', 'F', 'F'),
	('249ffa82-ee75-4c0e-b5bc-32a4870954f6', NULL, '123', '老张头', '123', 'old zhang', 1543296052681, '123', 0, '123', 1, '男', NULL, 123, '123', 'qrcode\\15\\13\\249ffa82-ee75-4c0e-b5bc-32a4870954f6.gif', 'F', 'F'),
	('8bec9fcd-286a-4c86-be23-d7ff633901f1', NULL, '123', '张三', '123', 'zhangsang', 1543289239023, '123', 0, '123@sina.com', 0, '男', NULL, 12, '123', 'qrcode\\0\\9\\8bec9fcd-286a-4c86-be23-d7ff633901f1.gif', 'F', 'F'),
	('945241a9-1710-40fe-8c99-6da4fd259f2c', NULL, '1230978', '张三之女', '123', 'zhang\'s daughter', 1543290715254, 'djfosdf92384', 0, '123@foxmail.com', 0, '女', NULL, 11, '123', 'qrcode\\8\\3\\945241a9-1710-40fe-8c99-6da4fd259f2c.gif', 'F', 'F'),
	('e005253b-86ed-4d9b-b00a-4b704c4fc1c0', NULL, '123', '张三之子', '123', 'zhang\'s son', 1543291077697, '北京市朝阳区', 0, '121237@sina.com', 0, '男', NULL, 12, '12312313', 'qrcode\\2\\10\\e005253b-86ed-4d9b-b00a-4b704c4fc1c0.gif', 'F', 'F'),
	('f2a01141-9bff-46ab-9b38-2f3ea9c1b67e', NULL, '123', '张三之女之子', '123', '123', 1543294830443, '123', 0, '123', 0, '男', NULL, 123, '123', 'qrcode\\2\\15\\f2a01141-9bff-46ab-9b38-2f3ea9c1b67e.gif', 'F', 'F'),
	('5ca974f3-e17f-4e46-ab0e-4a27a3645169', NULL, '12319208301', '新用户', '123', 'new user', 1546401566207, '123', 0, '123', 0, '男', NULL, 12, '123', 'qrcode\\3\\15\\5ca974f3-e17f-4e46-ab0e-4a27a3645169.gif', 'F', 'F'),
	('1681be4f-91b2-4bc1-8f8d-6da2afdc3d22', NULL, '1101012873891', '朝阳门', '123', '朝阳门', 1548901728891, '123', 0, '123', 0, '男', NULL, 123, '123', 'qrcode\\1\\12\\1681be4f-91b2-4bc1-8f8d-6da2afdc3d22.gif', 'F', 'F'),
	('67c31888-3ff8-4f6d-aa74-b333b8cdcfaa', 'okNKU0SmivI0uCUFLWJMZvQo34WA', NULL, '姜靓', '123', '过路风景', 1548915250943, '中国,北京,东城', 0, NULL, 0, '女', '1981-12-23', 37, '18601221725', 'qrcode\\1\\8\\67c31888-3ff8-4f6d-aa74-b333b8cdcfaa.gif', 'T', 'F'),
	('87653c4c-0139-4716-a933-124ee0c137d1', 'okNKU0aWzN2jlksajvRfcpQKl5PM', NULL, '王宇超', '123', '王宇超', 1548915251792, '中国,北京,东城', 0, NULL, 0, '男', '1987-9-1', 31, '13810038003', 'qrcode\\2\\2\\87653c4c-0139-4716-a933-124ee0c137d1.gif', 'T', 'F'),
	('7d6c9287-12ae-4626-846e-a1acdcec3b93', NULL, '1231245235234324', '呼家楼', '123', 'hjl', 1551073750449, '123', 0, '123', 0, '男', NULL, 123, '123', 'qrcode\\0\\12\\7d6c9287-12ae-4626-846e-a1acdcec3b93.gif', 'F', 'F'),
	('87dab0c5-1d1c-47bf-9124-65cb609a5823', NULL, '123', '核桃园', '123', 'hty', 1551073854696, '123', 0, '123', 0, '男', NULL, 123, '123', 'qrcode\\8\\6\\87dab0c5-1d1c-47bf-9124-65cb609a5823.gif', 'F', 'F'),
	('478e1049-d3df-47f9-9ffa-142fe8223494', NULL, '123', '老山', '123', 'laoshan', 1554162318626, '123', 0, '123', 0, '男', NULL, 123, '123', 'qrcode\\7\\7\\478e1049-d3df-47f9-9ffa-142fe8223494.gif', 'F', 'F'),
	('3d685e77-1301-40af-bfd0-481673234565', NULL, '123', '边府社区', '123', 'bfsocial', 1554791752951, '123', 0, 'dasldp@126.com', 0, '男', NULL, 123, '123', 'qrcode\\7\\1\\3d685e77-1301-40af-bfd0-481673234565.gif', 'F', 'F'),
	('3b56fdda-b916-4613-9b05-509c8c43dc18', 'okNKU0XwPZuXasixs5XGijY4CA9E', '', '高增云', '123', 'peter', 1557803826092, '中国,,', 0, '', 0, '男', '1983-7-15', 35, '13716026841', 'qrcode\\10\\11\\3b56fdda-b916-4613-9b05-509c8c43dc18.gif', 'T', 'F'),
	('47bf2baa-c268-4309-9dff-043c18d45923', 'okNKU0ZI8_qjT4CQUdutEAjYtP8Q', NULL, '三', '123', '盈', 1557814816336, '中国,北京,朝阳', 0, NULL, 0, '男', '1980-10-10', 38, '12345678900', 'qrcode\\3\\9\\47bf2baa-c268-4309-9dff-043c18d45923.gif', 'T', 'F');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 正在导出表  weixin.userapply4joinlevel 的数据：15 rows
/*!40000 ALTER TABLE `userapply4joinlevel` DISABLE KEYS */;
INSERT INTO `userapply4joinlevel` (`ua4jlid`, `timeStamp`, `timeStr`, `status`, `beread`, `theReason`, `theExpertise`, `theDesire`, `uid`, `index4user`, `levelName`, `levelDescription`) VALUES
	('402881e4687d8da501687da0d9da0000', 1548295920072, '2019-01-24 10:12:00', 1, b'0', '我喜欢参加', '写作', '成长', 'a0083e31-141a-4e12-b315-7621dc1017c6', 0, '体育公园4', '比较高4'),
	('402881e4687e77cc01687ea11e1a0000', 1548312714593, '2019-01-24 14:51:54', 2, b'0', '我喜欢', '写作', '升华', 'a0083e31-141a-4e12-b315-7621dc1017c6', 1, '体育公园3', '比较高3'),
	('402881e4687e77cc01687eac7d570003', 1548313460054, '2019-01-24 15:04:20', 1, b'0', '你好', '你好', '你好', 'a0083e31-141a-4e12-b315-7621dc1017c6', 2, '体育公园2', '比较高2'),
	('402881e4687e77cc01687ebf7eaf0005', 1548314705582, '2019-01-24 15:25:05', 1, b'0', '123', '123', '123', 'a0083e31-141a-4e12-b315-7621dc1017c6', 3, '体育公园', '比较高'),
	('402881e4688e641901688e6f9e150000', 1548577906193, '2019-01-27 16:31:46', 1, b'0', '非常好', '非常好', '非常好', 'a0083e31-141a-4e12-b315-7621dc1017c6', 4, '关东店志愿者', '关东店志愿者'),
	('402881e4688e641901688e750ad70002', 1548578261714, '2019-01-27 16:37:41', 0, b'0', '非常好', '非常好', '非常好', 'a0083e31-141a-4e12-b315-7621dc1017c6', 5, '关东店志愿者', '关东店志愿者'),
	('402881e468a1b12d0168a1be82840006', 1548901843587, '2019-01-31 10:30:43', 2, b'0', '喜欢', '写作', '强身健体', 'a0083e31-141a-4e12-b315-7621dc1017c6', 6, '朝阳门', '朝阳门街道办事处'),
	('402881e468a1b12d01689c9edf58000b', 1548815884118, '2019-01-30 10:38:04', 2, b'0', '喜欢', '绘画', '更好的生活', 'a0083e31-141a-4e12-b315-7621dc1017c6', 7, '朝内头条社区', '朝内头条社区居委会'),
	('402881e4689d05480168a28fc4e50002', 1548915557604, '2019-01-31 14:19:17', 2, b'0', '1', '1', '1', '87653c4c-0139-4716-a933-124ee0c137d1', 0, '朝内头条社区', '朝内头条社区居委会'),
	('402881e76a00c465016a00d0c0280002', 1554791841830, '2019-04-09 14:37:21', 2, b'0', '喜欢', '写作', '外向', 'a0083e31-141a-4e12-b315-7621dc1017c6', 8, '边府社区', '边府社区'),
	('402881e76a42c6e5016a42d694ff0000', 1555899520236, '2019-04-22 10:18:40', 2, b'0', 'aaa', 'aaa', 'aaa', 'a0083e31-141a-4e12-b315-7621dc1017c6', 9, '边府社区', '边府社区'),
	('402881e76a42c6e5016a42d9fbc00003', 1555899743167, '2019-04-22 10:22:23', 2, b'0', 'aaa', 'aaa', 'aaa', 'a0083e31-141a-4e12-b315-7621dc1017c6', 10, '呼家楼', '呼家楼街道'),
	('402881e86ab44be1016ab45a21dc0001', 1557803966939, '2019-05-14 11:19:26', 2, b'0', '文', '写作', '有木', '3b56fdda-b916-4613-9b05-509c8c43dc18', 0, '呼家楼', '呼家楼街道'),
	('402881e86ab4f517016ab501607b0002', 1557814927482, '2019-05-14 14:22:07', 2, b'0', '1', '2', '3', '47bf2baa-c268-4309-9dff-043c18d45923', 0, '东大桥', '东大桥'),
	('402881e86ad92f4c016ad9328e8e0000', 1558422130316, '2019-05-21 15:02:10', 2, b'0', '测试补充签到', '测试', '喜欢', 'a0083e31-141a-4e12-b315-7621dc1017c6', 11, '核桃园', '核桃园社区');
/*!40000 ALTER TABLE `userapply4joinlevel` ENABLE KEYS */;

-- 正在导出表  weixin.visitor 的数据：16 rows
/*!40000 ALTER TABLE `visitor` DISABLE KEYS */;
INSERT INTO `visitor` (`vid`, `startTime`, `endTime`, `score`, `workTime`, `uid`, `aid`, `index4user`, `index4activity`, `baomingTime`, `baomingTimeStr`, `startTimeStr`, `endTimeStr`) VALUES
	(2, -1, -1, -1, -1, 'a0083e31-141a-4e12-b315-7621dc1017c6', '5fad65a0-6b01-4133-a5aa-45c6cd2f9d00', 0, 0, 123, NULL, NULL, NULL),
	(3, 1558423304040, 1558423304040, 0, 0, 'a0083e31-141a-4e12-b315-7621dc1017c6', '10941f90-4b48-4c5b-a060-c7a348b0bbdc', 1, 0, 123, NULL, '2019-05-21 15:21', '2019-05-21 15:21'),
	(4, 1558423716507, 1558423716507, 1, 10800000, 'a0083e31-141a-4e12-b315-7621dc1017c6', '68ec9003-9176-44f3-b218-c1af49d0db11', 2, 0, 123, NULL, '2019-05-21 15:28', '2019-05-21 15:28'),
	(6, 1548725941610, -1, -1, -1, 'a0083e31-141a-4e12-b315-7621dc1017c6', '590e1fec-e519-4768-adce-aad72eaa4cb3', 3, 0, 123, '2019-1-22 12:22:22', '2019-1-22 12:22:22', '2019-1-22 12:22:22'),
	(7, 1548903342493, 1548906548838, 2, 3206345, 'a0083e31-141a-4e12-b315-7621dc1017c6', 'fd86169c-579b-4213-83d1-9e41626da4f6', 4, 0, 1548816508669, '2019-01-30 10:48:28', '2019-01-31 10:55', '2019-01-31 11:49'),
	(8, -1, -1, -1, -1, 'a0083e31-141a-4e12-b315-7621dc1017c6', 'ba078724-2d90-4d2e-ae06-cf9750f4d9ad', 5, 0, 1548823138271, '2019-01-30 12:38:58', NULL, NULL),
	(9, 1548914557991, -1, -1, -1, 'a0083e31-141a-4e12-b315-7621dc1017c6', '046e2849-270e-4cca-9a51-7d32eed3e5e8', 6, 0, 1548823147096, '2019-01-30 12:39:07', '2019-01-31 14:02', NULL),
	(10, -1, -1, -1, -1, 'a0083e31-141a-4e12-b315-7621dc1017c6', '40da48aa-43ab-4964-a0c0-565316780dc6', 7, 0, 1548823158416, '2019-01-30 12:39:18', NULL, NULL),
	(11, -1, -1, -1, -1, 'a0083e31-141a-4e12-b315-7621dc1017c6', '93725548-a9ac-427d-ad8e-0e2e14dbb7ff', 8, 0, 1548823165006, '2019-01-30 12:39:25', NULL, NULL),
	(12, -1, -1, -1, -1, '87653c4c-0139-4716-a933-124ee0c137d1', 'ff3261e7-9767-4160-ad98-5410f3bb3f2c', 0, 0, 1548916201297, '2019-01-31 14:30:01', NULL, NULL),
	(13, -1, -1, -1, -1, 'a0083e31-141a-4e12-b315-7621dc1017c6', 'ff3261e7-9767-4160-ad98-5410f3bb3f2c', 9, 1, 1548916287590, '2019-01-31 14:31:27', NULL, NULL),
	(14, 1554859851735, 1554859851735, 2, 3600000, 'a0083e31-141a-4e12-b315-7621dc1017c6', 'de1e69fd-5bf4-4226-8dc2-d79e0e0dc576', 10, 0, 1554794308418, '2019-04-09 15:18:28', '2019-04-10 09:30', '2019-04-10 09:30'),
	(15, 1554859802629, 1554859985652, 2, 183023, 'a0083e31-141a-4e12-b315-7621dc1017c6', 'ce22f1fd-8ae9-4086-ae01-6d4eaeb5fa13', 11, 0, 1554794321681, '2019-04-09 15:18:41', '2019-04-10 09:30', '2019-04-10 09:33'),
	(16, -1, -1, -1, -1, '47bf2baa-c268-4309-9dff-043c18d45923', 'a8d93bf9-83a8-4229-bc5f-061fee4fbf85', 0, 0, 1557815004317, '2019-05-14 14:23:24', NULL, NULL),
	(17, 1558512740830, 1558512740830, 1, 7200000, '449b89df-25ab-4f4f-b381-19a41a970bc7', '23f9ee28-e6be-4183-87cb-7fd30df7b012', 0, 0, 0, NULL, '2019-05-22 16:12', '2019-05-22 16:12'),
	(18, 1558512788047, 1558512788047, 1, 7200000, 'a0083e31-141a-4e12-b315-7621dc1017c6', '23f9ee28-e6be-4183-87cb-7fd30df7b012', 12, 1, 0, NULL, '2019-05-22 16:13', '2019-05-22 16:13');
/*!40000 ALTER TABLE `visitor` ENABLE KEYS */;

-- 正在导出表  weixin.ware 的数据：0 rows
/*!40000 ALTER TABLE `ware` DISABLE KEYS */;
/*!40000 ALTER TABLE `ware` ENABLE KEYS */;

-- 正在导出表  weixin.zerolevel 的数据：5 rows
/*!40000 ALTER TABLE `zerolevel` DISABLE KEYS */;
INSERT INTO `zerolevel` (`zid`, `name`, `description`, `level`, `qrcode`, `qrcodeTime`, `mflid`) VALUES
	('e9d0a77e-75d3-4f13-ac0a-7ffed167827e', '关东店', '关东店社区', 0, 'qrcode\\13\\0\\tag=zero&lid=e9d0a77e-75d3-4f13-ac0a-7ffed167827e.gif', 1543290585512, '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('ebf0f36c-efbf-4584-b579-8ecfb3c365e4', '核桃园', '核桃园社区', 0, 'qrcode\\4\\5\\tag=zero&lid=ebf0f36c-efbf-4584-b579-8ecfb3c365e4.gif', 1543288005959, '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('d16322ee-9d3f-418a-8cc9-bdd027858cbb', '朝内头条社区', '朝内头条社区居委会', 0, 'qrcode\\13\\11\\tag=zero&lid=d16322ee-9d3f-418a-8cc9-bdd027858cbb.gif', 1548901636670, 'd5718f48-2387-46c3-8c48-78d520540fd3'),
	('d5c460ba-6897-4d54-8b45-1b20e5eb6b68', '东大桥', '东大桥', 0, 'qrcode\\10\\15\\tag=zero&lid=d5c460ba-6897-4d54-8b45-1b20e5eb6b68.gif', 1553221199200, '47944819-f5ce-41cb-96da-a33ad8dc0284'),
	('e4b6911e-cbb1-462f-8a2b-9682d360c026', '边府社区', '边府社区', 0, 'qrcode\\0\\2\\tag=zero&lid=e4b6911e-cbb1-462f-8a2b-9682d360c026.gif', 1554162280455, '2c1c8666-b43f-44b9-90ca-ac5c2de4c80a');
/*!40000 ALTER TABLE `zerolevel` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
