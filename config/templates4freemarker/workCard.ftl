<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<?mso-application progid="Word.Document"?>
<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
	<pkg:part pkg:name="/_rels/.rels"
		pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
		<pkg:xmlData>
			<Relationships
				xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
				<Relationship Id="rId4"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"
					Target="word/document.xml" />
				<Relationship Id="rId2"
					Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties"
					Target="docProps/core.xml" />
				<Relationship Id="rId1"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties"
					Target="docProps/app.xml" />
				<Relationship Id="rId3"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties"
					Target="docProps/custom.xml" />
			</Relationships>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/_rels/document.xml.rels"
		pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
		<pkg:xmlData>
			<Relationships
				xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
				<Relationship Id="rId6"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable"
					Target="fontTable.xml" />
				<Relationship Id="rId5"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXml"
					Target="../customXml/item1.xml" />







				<!--============================================ 图片资源索引-开始==================================================== -->
				<#if cards?? && (cards?size > 0)>
				<#list cards as c>
				<Relationship Id="rId${c_index}Gif"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image"
					Target="media/image${c_index}.GIF" />
				</#list>
				</#if>
				<!--============================================ 图片资源索引-结束==================================================== -->








				<Relationship Id="rId3"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme"
					Target="theme/theme1.xml" />
				<Relationship Id="rId2"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings"
					Target="settings.xml" />
				<Relationship Id="rId1"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles"
					Target="styles.xml" />
			</Relationships>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/document.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml">
		<pkg:xmlData>
			<w:document
				xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
				xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
				xmlns:o="urn:schemas-microsoft-com:office:office"
				xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
				xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
				xmlns:v="urn:schemas-microsoft-com:vml"
				xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing"
				xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
				xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
				xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
				xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
				xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup"
				xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk"
				xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
				xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"
				xmlns:wpsCustomData="http://www.wps.cn/officeDocument/2013/wpsCustomData"
				mc:Ignorable="w14 w15 wp14">
				<w:body>
					<w:p>
						<w:pPr>
							<w:rPr>
								<w:sz w:val="21" />
								<w:vertAlign w:val="baseline" />
							</w:rPr>
						</w:pPr>






						<!--============================================ 图片-开始==================================================== -->

						<#if cards?? && (cards?size > 0)>
						<#list cards as c>
						<w:r>
							<w:rPr>
								<w:sz w:val="21" />
							</w:rPr>
							<mc:AlternateContent>
								<!--第一部分 -->
								<mc:Choice Requires="wpc">
									<w:drawing>
										<wp:inline distT="0" distB="0" distL="114300" distR="114300">
											<wp:extent cx="1965325" cy="3096260" />
											<wp:effectExtent l="9525" t="9525" r="25400"
												b="18415" />
											<wp:docPr id="draw${c_index}" name="画布 ${c_index}" />
											<wp:cNvGraphicFramePr>
												<a:graphicFrameLocks
													xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"
													noChangeAspect="1" />
											</wp:cNvGraphicFramePr>
											<a:graphic
												xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
												<a:graphicData
													uri="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas">
													<wpc:wpc>
														<wpc:bg />
														<wpc:whole>
															<a:ln w="19050">
																<a:solidFill>
																	<a:srgbClr val="2E75B6" />
																</a:solidFill>
															</a:ln>
														</wpc:whole>
														<!-- 文本框1 -->
														<wps:wsp>
															<wps:cNvPr id="textbox${c_index}cNvPr_1" name="文本框 ${c_index}cNvPr_1" />
															<wps:cNvSpPr txBox="1" />
															<wps:spPr>
																<a:xfrm>
																	<a:off x="98425" y="750570" />
																	<a:ext cx="1763395" cy="564515" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
																<a:solidFill>
																	<a:schemeClr val="lt1" />
																</a:solidFill>
																<a:ln w="6350">
																	<a:noFill />
																</a:ln>
															</wps:spPr>
															<wps:style>
																<a:lnRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:lnRef>
																<a:fillRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:fillRef>
																<a:effectRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:effectRef>
																<a:fontRef idx="minor">
																	<a:schemeClr val="dk1" />
																</a:fontRef>
															</wps:style>
															<wps:txbx>
																<w:txbxContent>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="140" w:lineRule="exact" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t>工作证使用说明</w:t>
																		</w:r>
																	</w:p>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="140" w:lineRule="exact" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:rFonts w:hint="default" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t xml:space="preserve">· </w:t>
																		</w:r>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t>二维码作为您在社区活动电子签到和积分管理的唯一凭证，请妥善保护</w:t>
																		</w:r>
																	</w:p>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="140" w:lineRule="exact" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:rFonts w:hint="default" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t xml:space="preserve">· </w:t>
																		</w:r>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																					w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																					w:cs="Microsoft YaHei UI" />
																				<w:b w:val="0" />
																				<w:bCs w:val="0" />
																				<w:color w:val="A6A6A6" w:themeColor="background1"
																					w:themeShade="A6" />
																				<w:sz w:val="11" />
																				<w:szCs w:val="11" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t>
																				工作证有效期截止于19年10月，之后将在志愿者回馈日统一收回，请您妥善保存。
																			</w:t>
																		</w:r>
																	</w:p>
																</w:txbxContent>
															</wps:txbx>
															<wps:bodyPr rot="0" spcFirstLastPara="0"
																vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																wrap="square" lIns="91440" tIns="45720" rIns="91440"
																bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																fromWordArt="0" anchor="t" anchorCtr="0" forceAA="0"
																compatLnSpc="1">
																<a:noAutofit />
															</wps:bodyPr>
														</wps:wsp>
														<!-- 二维码 -->
														<pic:pic
															xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
															<pic:nvPicPr>
																<pic:cNvPr id="pic${c_index}cNvPr" name="图片 ${c_index}cNvPr"
																	descr="志愿者的专属二维码" />
																<pic:cNvPicPr>
																	<a:picLocks noChangeAspect="1" />
																</pic:cNvPicPr>
															</pic:nvPicPr>
															<pic:blipFill>
																<a:blip r:embed="rId${c_index}Gif" />
																<a:stretch>
																	<a:fillRect />
																</a:stretch>
															</pic:blipFill>
															<pic:spPr>
																<a:xfrm>
																	<a:off x="657225" y="1387475" />
																	<a:ext cx="614680" cy="614680" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
																<a:ln w="28575" cmpd="sng">
																	<a:solidFill>
																		<a:schemeClr val="accent1">
																			<a:shade val="50000" />
																		</a:schemeClr>
																	</a:solidFill>
																	<a:prstDash val="solid" />
																</a:ln>
															</pic:spPr>
														</pic:pic>
														<!-- 矩形1 -->
														<wps:wsp>
															<wps:cNvPr id="rectangle${c_index}cNvPr_1"
																name="矩形 ${c_index}cNvPr_1" />
															<wps:cNvSpPr />
															<wps:spPr>
																<a:xfrm>
																	<a:off x="0" y="0" />
																	<a:ext cx="1509395" cy="659765" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
																<a:solidFill>
																	<a:schemeClr val="accent1">
																		<a:lumMod val="75000" />
																	</a:schemeClr>
																</a:solidFill>
															</wps:spPr>
															<wps:style>
																<a:lnRef idx="2">
																	<a:schemeClr val="accent1">
																		<a:shade val="50000" />
																	</a:schemeClr>
																</a:lnRef>
																<a:fillRef idx="1">
																	<a:schemeClr val="accent1" />
																</a:fillRef>
																<a:effectRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:effectRef>
																<a:fontRef idx="minor">
																	<a:schemeClr val="lt1" />
																</a:fontRef>
															</wps:style>
															<wps:bodyPr rot="0" spcFirstLastPara="0"
																vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																wrap="square" lIns="91440" tIns="45720" rIns="91440"
																bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																fromWordArt="0" anchor="ctr" anchorCtr="0" forceAA="0"
																compatLnSpc="1">
																<a:noAutofit />
															</wps:bodyPr>
														</wps:wsp>
														<!--文本框2 -->
														<wps:wsp>
															<wps:cNvPr id="textbox${c_index}cNvPr_2" name="文本框 ${c_index}cNvPr_2" />
															<wps:cNvSpPr txBox="1" />
															<wps:spPr>
																<a:xfrm>
																	<a:off x="0" y="148974" />
																	<a:ext cx="1111046" cy="377462" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
																<a:noFill />
																<a:ln w="6350">
																	<a:noFill />
																</a:ln>
															</wps:spPr>
															<wps:style>
																<a:lnRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:lnRef>
																<a:fillRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:fillRef>
																<a:effectRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:effectRef>
																<a:fontRef idx="minor">
																	<a:schemeClr val="dk1" />
																</a:fontRef>
															</wps:style>
															<wps:txbx>
																<w:txbxContent>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="300" w:lineRule="exact" />
																			<w:jc w:val="left" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																					w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																				<w:color w:val="FFFFFF" w:themeColor="background1" />
																				<w:sz w:val="30" />
																				<w:szCs w:val="30" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				<w14:glow w14:rad="0">
																					<w14:srgbClr w14:val="000000" />
																				</w14:glow>
																				<w14:shadow w14:blurRad="38100" w14:dist="19050"
																					w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																					w14:kx="0" w14:ky="0" w14:algn="tl">
																					<w14:schemeClr w14:val="dk1">
																						<w14:alpha w14:val="60000" />
																					</w14:schemeClr>
																				</w14:shadow>
																				<w14:reflection w14:blurRad="0"
																					w14:stA="0" w14:stPos="0" w14:endA="0" w14:endPos="0"
																					w14:dist="0" w14:dir="0" w14:fadeDir="0" w14:sx="0"
																					w14:sy="0" w14:kx="0" w14:ky="0" w14:algn="none" />
																				<w14:textFill>
																					<w14:solidFill>
																						<w14:schemeClr w14:val="bg1" />
																					</w14:solidFill>
																				</w14:textFill>
																				<w14:props3d w14:extrusionH="0"
																					w14:contourW="0" w14:prstMaterial="clear" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																					w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																				<w:color w:val="FFFFFF" w:themeColor="background1" />
																				<w:sz w:val="30" />
																				<w:szCs w:val="30" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				<w14:glow w14:rad="0">
																					<w14:srgbClr w14:val="000000" />
																				</w14:glow>
																				<w14:shadow w14:blurRad="38100" w14:dist="19050"
																					w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																					w14:kx="0" w14:ky="0" w14:algn="tl">
																					<w14:schemeClr w14:val="dk1">
																						<w14:alpha w14:val="60000" />
																					</w14:schemeClr>
																				</w14:shadow>
																				<w14:reflection w14:blurRad="0"
																					w14:stA="0" w14:stPos="0" w14:endA="0" w14:endPos="0"
																					w14:dist="0" w14:dir="0" w14:fadeDir="0" w14:sx="0"
																					w14:sy="0" w14:kx="0" w14:ky="0" w14:algn="none" />
																				<w14:textFill>
																					<w14:solidFill>
																						<w14:schemeClr w14:val="bg1" />
																					</w14:solidFill>
																				</w14:textFill>
																				<w14:props3d w14:extrusionH="0"
																					w14:contourW="0" w14:prstMaterial="clear" />
																			</w:rPr>
																			<w:t>工作证</w:t>
																		</w:r>
																	</w:p>
																</w:txbxContent>
															</wps:txbx>
															<wps:bodyPr rot="0" spcFirstLastPara="0"
																vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																wrap="square" lIns="91440" tIns="45720" rIns="91440"
																bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																fromWordArt="0" anchor="t" anchorCtr="0" forceAA="0"
																compatLnSpc="1">
																<a:noAutofit />
															</wps:bodyPr>
														</wps:wsp>
														<!--文本框3 -->
														<wps:wsp>
															<wps:cNvPr id="textbox${c_index}cNvPr_3" name="文本框 ${c_index}cNvPr_3" />
															<wps:cNvSpPr txBox="1" />
															<wps:spPr>
																<a:xfrm>
																	<a:off x="422275" y="411480" />
																	<a:ext cx="1149985" cy="222250" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
																<a:noFill />
																<a:ln w="6350">
																	<a:noFill />
																</a:ln>
															</wps:spPr>
															<wps:style>
																<a:lnRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:lnRef>
																<a:fillRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:fillRef>
																<a:effectRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:effectRef>
																<a:fontRef idx="minor">
																	<a:schemeClr val="dk1" />
																</a:fontRef>
															</wps:style>
															<wps:txbx>
																<w:txbxContent>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="200" w:lineRule="exact" />
																			<w:jc w:val="left" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																					w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																				<w:color w:val="FFFFFF" w:themeColor="background1" />
																				<w:sz w:val="15" />
																				<w:szCs w:val="15" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				<w14:glow w14:rad="0">
																					<w14:srgbClr w14:val="000000" />
																				</w14:glow>
																				<w14:shadow w14:blurRad="38100" w14:dist="19050"
																					w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																					w14:kx="0" w14:ky="0" w14:algn="tl">
																					<w14:schemeClr w14:val="dk1">
																						<w14:alpha w14:val="60000" />
																					</w14:schemeClr>
																				</w14:shadow>
																				<w14:reflection w14:blurRad="0"
																					w14:stA="0" w14:stPos="0" w14:endA="0" w14:endPos="0"
																					w14:dist="0" w14:dir="0" w14:fadeDir="0" w14:sx="0"
																					w14:sy="0" w14:kx="0" w14:ky="0" w14:algn="none" />
																				<w14:textFill>
																					<w14:solidFill>
																						<w14:schemeClr w14:val="bg1" />
																					</w14:solidFill>
																				</w14:textFill>
																				<w14:props3d w14:extrusionH="0"
																					w14:contourW="0" w14:prstMaterial="clear" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																					w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																				<w:color w:val="FFFFFF" w:themeColor="background1" />
																				<w:sz w:val="15" />
																				<w:szCs w:val="15" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				<w14:glow w14:rad="0">
																					<w14:srgbClr w14:val="000000" />
																				</w14:glow>
																				<w14:shadow w14:blurRad="38100" w14:dist="19050"
																					w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																					w14:kx="0" w14:ky="0" w14:algn="tl">
																					<w14:schemeClr w14:val="dk1">
																						<w14:alpha w14:val="60000" />
																					</w14:schemeClr>
																				</w14:shadow>
																				<w14:reflection w14:blurRad="0"
																					w14:stA="0" w14:stPos="0" w14:endA="0" w14:endPos="0"
																					w14:dist="0" w14:dir="0" w14:fadeDir="0" w14:sx="0"
																					w14:sy="0" w14:kx="0" w14:ky="0" w14:algn="none" />
																				<w14:textFill>
																					<w14:solidFill>
																						<w14:schemeClr w14:val="bg1" />
																					</w14:solidFill>
																				</w14:textFill>
																				<w14:props3d w14:extrusionH="0"
																					w14:contourW="0" w14:prstMaterial="clear" />
																			</w:rPr>
																			<w:t>${(c.minusFirstName)!''}${(c.zeroName)!''}</w:t>
																		</w:r>
																	</w:p>
																</w:txbxContent>
															</wps:txbx>
															<wps:bodyPr rot="0" spcFirstLastPara="0"
																vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																wrap="square" lIns="91440" tIns="45720" rIns="91440"
																bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																fromWordArt="0" anchor="t" anchorCtr="0" forceAA="0"
																compatLnSpc="1">
																<a:noAutofit />
															</wps:bodyPr>
														</wps:wsp>
														<!--矩形2 -->
														<wps:wsp>
															<wps:cNvPr id="rectangle${c_index}cNvPr_2"
																name="矩形 ${c_index}cNvPr_2" />
															<wps:cNvSpPr />
															<wps:spPr>
																<a:xfrm>
																	<a:off x="1557655" y="0" />
																	<a:ext cx="407670" cy="662940" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
															</wps:spPr>
															<wps:style>
																<a:lnRef idx="2">
																	<a:schemeClr val="accent1">
																		<a:shade val="50000" />
																	</a:schemeClr>
																</a:lnRef>
																<a:fillRef idx="1">
																	<a:schemeClr val="accent1" />
																</a:fillRef>
																<a:effectRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:effectRef>
																<a:fontRef idx="minor">
																	<a:schemeClr val="lt1" />
																</a:fontRef>
															</wps:style>
															<wps:bodyPr rot="0" spcFirstLastPara="0"
																vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																wrap="square" lIns="91440" tIns="45720" rIns="91440"
																bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																fromWordArt="0" anchor="ctr" anchorCtr="0" forceAA="0"
																compatLnSpc="1">
																<a:noAutofit />
															</wps:bodyPr>
														</wps:wsp>
														<!--文本框4 -->
														<wps:wsp>
															<wps:cNvPr id="textbox${c_index}cNvPr_4" name="文本框 ${c_index}cNvPr_4" />
															<wps:cNvSpPr txBox="1" />
															<wps:spPr>
																<a:xfrm>
																	<a:off x="1567180" y="177800" />
																	<a:ext cx="398145" cy="274320" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
																<a:noFill />
																<a:ln w="6350">
																	<a:noFill />
																</a:ln>
															</wps:spPr>
															<wps:style>
																<a:lnRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:lnRef>
																<a:fillRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:fillRef>
																<a:effectRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:effectRef>
																<a:fontRef idx="minor">
																	<a:schemeClr val="dk1" />
																</a:fontRef>
															</wps:style>
															<wps:txbx>
																<w:txbxContent>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="320" w:lineRule="exact" />
																			<w:jc w:val="left" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																					w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																				<w:color w:val="FFFFFF" w:themeColor="background1" />
																				<w:w w:val="80" />
																				<w:sz w:val="32" />
																				<w:szCs w:val="32" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				<w14:glow w14:rad="0">
																					<w14:srgbClr w14:val="000000" />
																				</w14:glow>
																				<w14:shadow w14:blurRad="38100" w14:dist="19050"
																					w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																					w14:kx="0" w14:ky="0" w14:algn="tl">
																					<w14:schemeClr w14:val="dk1">
																						<w14:alpha w14:val="60000" />
																					</w14:schemeClr>
																				</w14:shadow>
																				<w14:reflection w14:blurRad="0"
																					w14:stA="0" w14:stPos="0" w14:endA="0" w14:endPos="0"
																					w14:dist="0" w14:dir="0" w14:fadeDir="0" w14:sx="0"
																					w14:sy="0" w14:kx="0" w14:ky="0" w14:algn="none" />
																				<w14:textFill>
																					<w14:solidFill>
																						<w14:schemeClr w14:val="bg1" />
																					</w14:solidFill>
																				</w14:textFill>
																				<w14:props3d w14:extrusionH="0"
																					w14:contourW="0" w14:prstMaterial="clear" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																					w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																				<w:color w:val="FFFFFF" w:themeColor="background1" />
																				<w:w w:val="80" />
																				<w:sz w:val="32" />
																				<w:szCs w:val="32" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				<w14:glow w14:rad="0">
																					<w14:srgbClr w14:val="000000" />
																				</w14:glow>
																				<w14:shadow w14:blurRad="38100" w14:dist="19050"
																					w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																					w14:kx="0" w14:ky="0" w14:algn="tl">
																					<w14:schemeClr w14:val="dk1">
																						<w14:alpha w14:val="60000" />
																					</w14:schemeClr>
																				</w14:shadow>
																				<w14:reflection w14:blurRad="0"
																					w14:stA="0" w14:stPos="0" w14:endA="0" w14:endPos="0"
																					w14:dist="0" w14:dir="0" w14:fadeDir="0" w14:sx="0"
																					w14:sy="0" w14:kx="0" w14:ky="0" w14:algn="none" />
																				<w14:textFill>
																					<w14:solidFill>
																						<w14:schemeClr w14:val="bg1" />
																					</w14:solidFill>
																				</w14:textFill>
																				<w14:props3d w14:extrusionH="0"
																					w14:contourW="0" w14:prstMaterial="clear" />
																			</w:rPr>
																			<w:t>${(c.indexNum)!''}</w:t>
																		</w:r>
																	</w:p>
																</w:txbxContent>
															</wps:txbx>
															<wps:bodyPr rot="0" spcFirstLastPara="0"
																vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																wrap="square" lIns="91440" tIns="45720" rIns="91440"
																bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																fromWordArt="0" anchor="t" anchorCtr="0" forceAA="0"
																compatLnSpc="1">
																<a:noAutofit />
															</wps:bodyPr>
														</wps:wsp>
														<!--组合1 -->
														<wpg:wgp>
															<wpg:cNvPr id="combine${c_index+1}cNvPr_1"
																name="组合 ${c_index}cNvPr_1" />
															<wpg:cNvGrpSpPr />
															<wpg:grpSpPr>
																<a:xfrm>
																	<a:off x="335280" y="2331720" />
																	<a:ext cx="1198245" cy="607060" />
																	<a:chOff x="181" y="4962" />
																	<a:chExt cx="3057" cy="1604" />
																</a:xfrm>
															</wpg:grpSpPr>
															<wps:wsp>
																<wps:cNvPr id="angleRectangle ${c_index}cNvPr"
																	name="圆角矩形 ${c_index}cNvPr" />
																<wps:cNvSpPr />
																<wps:spPr>
																	<a:xfrm>
																		<a:off x="330" y="4962" />
																		<a:ext cx="2909" cy="1605" />
																	</a:xfrm>
																	<a:prstGeom prst="roundRect">
																		<a:avLst />
																	</a:prstGeom>
																	<a:solidFill>
																		<a:srgbClr val="227A8F" />
																	</a:solidFill>
																	<a:ln>
																		<a:solidFill>
																			<a:srgbClr val="227A8F" />
																		</a:solidFill>
																	</a:ln>
																</wps:spPr>
																<wps:style>
																	<a:lnRef idx="2">
																		<a:schemeClr val="accent1">
																			<a:shade val="50000" />
																		</a:schemeClr>
																	</a:lnRef>
																	<a:fillRef idx="1">
																		<a:schemeClr val="accent1" />
																	</a:fillRef>
																	<a:effectRef idx="0">
																		<a:schemeClr val="accent1" />
																	</a:effectRef>
																	<a:fontRef idx="minor">
																		<a:schemeClr val="lt1" />
																	</a:fontRef>
																</wps:style>
																<wps:bodyPr rot="0" spcFirstLastPara="0"
																	vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																	wrap="square" lIns="91440" tIns="45720" rIns="91440"
																	bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																	fromWordArt="0" anchor="ctr" anchorCtr="0" forceAA="0"
																	compatLnSpc="1">
																	<a:noAutofit />
																</wps:bodyPr>
															</wps:wsp>
															<!--文本框5 -->
															<wps:wsp>
																<wps:cNvPr id="textbox${c_index}cNvPr_5" name="文本框 ${c_index}cNvPr_5" />
																<wps:cNvSpPr txBox="1" />
																<wps:spPr>
																	<a:xfrm>
																		<a:off x="181" y="5067" />
																		<a:ext cx="991" cy="1448" />
																	</a:xfrm>
																	<a:prstGeom prst="rect">
																		<a:avLst />
																	</a:prstGeom>
																	<a:noFill />
																	<a:ln w="28575" cap="rnd" cmpd="sng">
																		<a:noFill />
																		<a:prstDash val="solid" />
																		<a:round />
																	</a:ln>
																</wps:spPr>
																<wps:style>
																	<a:lnRef idx="0">
																		<a:schemeClr val="accent1" />
																	</a:lnRef>
																	<a:fillRef idx="0">
																		<a:schemeClr val="accent1" />
																	</a:fillRef>
																	<a:effectRef idx="0">
																		<a:schemeClr val="accent1" />
																	</a:effectRef>
																	<a:fontRef idx="minor">
																		<a:schemeClr val="dk1" />
																	</a:fontRef>
																</wps:style>
																<wps:txbx>
																	<w:txbxContent>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:jc w:val="center" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																				<w:t>姓名</w:t>
																			</w:r>
																		</w:p>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:jc w:val="center" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																				<w:t>性别</w:t>
																			</w:r>
																		</w:p>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:jc w:val="center" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																				<w:t>电话</w:t>
																			</w:r>
																		</w:p>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:jc w:val="center" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:color w:val="FFFFFF" w:themeColor="background1" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																					<w14:textFill>
																						<w14:solidFill>
																							<w14:schemeClr w14:val="bg1" />
																						</w14:solidFill>
																					</w14:textFill>
																				</w:rPr>
																				<w:t>团队</w:t>
																			</w:r>
																		</w:p>
																	</w:txbxContent>
																</wps:txbx>
																<wps:bodyPr rot="0" spcFirstLastPara="0"
																	vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																	wrap="square" lIns="91440" tIns="45720" rIns="91440"
																	bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																	fromWordArt="0" anchor="t" anchorCtr="0" forceAA="0"
																	compatLnSpc="1">
																	<a:noAutofit />
																</wps:bodyPr>
															</wps:wsp>
															<!--文本框6 -->
															<wps:wsp>
																<wps:cNvPr id="textbox${c_index}cNvPr_6" name="文本框 ${c_index}cNvPr_6" />
																<wps:cNvSpPr txBox="1" />
																<wps:spPr>
																	<a:xfrm>
																		<a:off x="997" y="5068" />
																		<a:ext cx="1965" cy="1361" />
																	</a:xfrm>
																	<a:prstGeom prst="rect">
																		<a:avLst />
																	</a:prstGeom>
																	<a:solidFill>
																		<a:schemeClr val="lt1" />
																	</a:solidFill>
																	<a:ln w="6350">
																		<a:noFill />
																	</a:ln>
																</wps:spPr>
																<wps:style>
																	<a:lnRef idx="0">
																		<a:schemeClr val="accent1" />
																	</a:lnRef>
																	<a:fillRef idx="0">
																		<a:schemeClr val="accent1" />
																	</a:fillRef>
																	<a:effectRef idx="0">
																		<a:schemeClr val="accent1" />
																	</a:effectRef>
																	<a:fontRef idx="minor">
																		<a:schemeClr val="dk1" />
																	</a:fontRef>
																</wps:style>
																<wps:txbx>
																	<w:txbxContent>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																				<w:t>${(c.username)!''}</w:t>
																			</w:r>
																		</w:p>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																				<w:t>${(c.sex)!''}</w:t>
																			</w:r>
																		</w:p>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																				<w:t>${(c.phone)!''}</w:t>
																			</w:r>
																		</w:p>
																		<w:p>
																			<w:pPr>
																				<w:keepNext w:val="0" />
																				<w:keepLines w:val="0" />
																				<w:pageBreakBefore w:val="0" />
																				<w:widowControl w:val="0" />
																				<w:kinsoku />
																				<w:wordWrap />
																				<w:overflowPunct />
																				<w:topLinePunct w:val="0" />
																				<w:autoSpaceDE />
																				<w:autoSpaceDN />
																				<w:bidi w:val="0" />
																				<w:adjustRightInd />
																				<w:snapToGrid />
																				<w:spacing w:line="160" w:lineRule="exact" />
																				<w:textAlignment w:val="auto" />
																				<w:rPr>
																					<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																			</w:pPr>
																			<w:r>
																				<w:rPr>
																					<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																						w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																					<w:sz w:val="10" />
																					<w:szCs w:val="10" />
																					<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																				</w:rPr>
																				<w:t>${(c.levelName)!''}</w:t>
																			</w:r>
																		</w:p>
																	</w:txbxContent>
																</wps:txbx>
																<wps:bodyPr rot="0" spcFirstLastPara="0"
																	vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																	wrap="square" lIns="91440" tIns="45720" rIns="91440"
																	bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																	fromWordArt="0" anchor="t" anchorCtr="0" forceAA="0"
																	compatLnSpc="1">
																	<a:noAutofit />
																</wps:bodyPr>
															</wps:wsp>
														</wpg:wgp>
														<!--文本框7 -->
														<wps:wsp>
															<wps:cNvPr id="textbox${c_index}cNvPr_7" name="文本框 ${c_index}cNvPr_7" />
															<wps:cNvSpPr txBox="1" />
															<wps:spPr>
																<a:xfrm>
																	<a:off x="608965" y="1990090" />
																	<a:ext cx="704850" cy="281305" />
																</a:xfrm>
																<a:prstGeom prst="rect">
																	<a:avLst />
																</a:prstGeom>
																<a:noFill />
																<a:ln w="6350">
																	<a:noFill />
																</a:ln>
															</wps:spPr>
															<wps:style>
																<a:lnRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:lnRef>
																<a:fillRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:fillRef>
																<a:effectRef idx="0">
																	<a:schemeClr val="accent1" />
																</a:effectRef>
																<a:fontRef idx="minor">
																	<a:schemeClr val="dk1" />
																</a:fontRef>
															</wps:style>
															<wps:txbx>
																<w:txbxContent>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="140" w:lineRule="exact" />
																			<w:jc w:val="center" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:rFonts w:hint="default" w:eastAsiaTheme="minorEastAsia" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="7F7F7F" w:themeColor="background1"
																					w:themeShade="80" />
																				<w:w w:val="100" />
																				<w:sz w:val="10" />
																				<w:szCs w:val="10" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="7F7F7F" w:themeColor="background1"
																					w:themeShade="80" />
																				<w:w w:val="100" />
																				<w:sz w:val="10" />
																				<w:szCs w:val="10" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t>二维码</w:t>
																		</w:r>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia"
																					w:eastAsiaTheme="minorEastAsia" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="7F7F7F" w:themeColor="background1"
																					w:themeShade="80" />
																				<w:w w:val="100" />
																				<w:sz w:val="10" />
																				<w:szCs w:val="10" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t>仅向领队</w:t>
																		</w:r>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="7F7F7F" w:themeColor="background1"
																					w:themeShade="80" />
																				<w:w w:val="100" />
																				<w:sz w:val="10" />
																				<w:szCs w:val="10" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t>或</w:t>
																		</w:r>
																	</w:p>
																	<w:p>
																		<w:pPr>
																			<w:keepNext w:val="0" />
																			<w:keepLines w:val="0" />
																			<w:pageBreakBefore w:val="0" />
																			<w:widowControl w:val="0" />
																			<w:kinsoku />
																			<w:wordWrap />
																			<w:overflowPunct />
																			<w:topLinePunct w:val="0" />
																			<w:autoSpaceDE />
																			<w:autoSpaceDN />
																			<w:bidi w:val="0" />
																			<w:adjustRightInd />
																			<w:snapToGrid />
																			<w:spacing w:line="140" w:lineRule="exact" />
																			<w:jc w:val="center" />
																			<w:textAlignment w:val="auto" />
																			<w:rPr>
																				<w:sz w:val="10" />
																				<w:szCs w:val="10" />
																			</w:rPr>
																		</w:pPr>
																		<w:r>
																			<w:rPr>
																				<w:rFonts w:hint="eastAsia"
																					w:eastAsiaTheme="minorEastAsia" />
																				<w:b />
																				<w:bCs />
																				<w:color w:val="7F7F7F" w:themeColor="background1"
																					w:themeShade="80" />
																				<w:w w:val="100" />
																				<w:sz w:val="10" />
																				<w:szCs w:val="10" />
																				<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																			</w:rPr>
																			<w:t>社区工作者出示</w:t>
																		</w:r>
																	</w:p>
																</w:txbxContent>
															</wps:txbx>
															<wps:bodyPr rot="0" spcFirstLastPara="0"
																vertOverflow="overflow" horzOverflow="overflow" vert="horz"
																wrap="square" lIns="91440" tIns="45720" rIns="91440"
																bIns="45720" numCol="1" spcCol="0" rtlCol="0"
																fromWordArt="0" anchor="t" anchorCtr="0" forceAA="0"
																compatLnSpc="1">
																<a:noAutofit />
															</wps:bodyPr>
														</wps:wsp>
													</wpc:wpc>
												</a:graphicData>
											</a:graphic>
										</wp:inline>
									</w:drawing>
								</mc:Choice>
								<!--第二部分 -->
								<mc:Fallback>
									<w:pict>
										<v:group id="_x0000_s1026" o:spid="_x0000_s1026"
											o:spt="203" style="height:243.8pt;width:154.75pt;" coordsize="1965325,3096260"
											editas="canvas"
											o:gfxdata="UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAEAAAAZHJzL1BLAwQUAAAACACHTuJAAhLEadcAAAAF&#10;AQAADwAAAGRycy9kb3ducmV2LnhtbE2PwU7DMBBE70j8g7VIXBC1S2loQ5wKKsGBU2kr0aNrL0lE&#10;vI5ipy1/z8KFXlYazWjmbbE4+VYcsI9NIA3jkQKBZINrqNKw3bzczkDEZMiZNhBq+MYIi/LyojC5&#10;C0d6x8M6VYJLKOZGQ51Sl0sZbY3exFHokNj7DL03iWVfSdebI5f7Vt4plUlvGuKF2nS4rNF+rQev&#10;wVu7q57k5GMVs+45Te3N2+ty0Pr6aqweQSQ8pf8w/OIzOpTMtA8DuShaDfxI+rvsTdR8CmKv4X72&#10;kIEsC3lOX/4AUEsDBBQAAAAIAIdO4kBcDd56eAYAAAQjAAAOAAAAZHJzL2Uyb0RvYy54bWztWt1u&#10;HDUUvkfiHay5T3c8/7PKpkqTpqoUaERBXHs9M7sjZsaD7c2mvQW1cIcQ4oZKCG4QEg+AVMHbpOEx&#10;OLZnZn+yIcmWbinalbKxxx7/HH/nnO8c7+7ds7JApykXOasGFr5jWyitKEvyajSwPvn4aCeykJCk&#10;SkjBqnRgPUmFdXfv/fd2p3U/ddiYFUnKEQxSif60HlhjKet+ryfoOC2JuMPqtILGjPGSSKjyUS/h&#10;ZAqjl0XPse2gN2U8qTmjqRDw9NA0Wnt6/CxLqXyUZSKVqBhYsDapv7n+Hqrv3t4u6Y84qcc5bZZB&#10;1lhFSfIKJu2GOiSSoAnPLw1V5pQzwTJ5h7Kyx7Isp6neA+wG20u7ecDZpNZ7GfWno7oTE4h2SU5r&#10;D0s/PD3hKE/g7CxUkRKO6OK7l+e/f4Gwks20HvWhywNeP65PePNgZGpqu2cZL9V/2Ag601J90kk1&#10;PZOIwkMcB77r+Bai0ObaceAEjdzpGA7n0nt0fP+aN3vtxD21vm450xowJGZiEq8npsdjUqda+kLJ&#10;oBGTE7RyevX981cvfnv10zPkGlHpbkpOSJ7dY2rn7XMBD1eIK448JRcQS+jbfthIpZNbGLhu3MjN&#10;Dzwf+2q8bvOkX3MhH6SsRKowsDigXYOQnB4Labq2XdTsghV5cpQXha7w0fCg4OiUgGYc6U8z+kK3&#10;okLTgRW4vq1Hrph63wxdVLAYJXOzO1WSZ8MzDRLRH7LkCUiCM6NyoqZHOazymAh5QjjoGGgj2A35&#10;CL6ygsEkrClZaMz401XPVX84U2i10BR0dmCJzyeEpxYqHlZw2jH2PKXkuuL5oQMVPt8ynG+pJuUB&#10;g80D8GF1uqj6y6ItZpyVn4J52VezQhOpKMw9sGRbPJDGkoB5oun+vu4Eal0TeVw9rqkaWom6YvsT&#10;ybJcH4kSk5FNIz1A7N5undM+/DUaDqVL0L3eEsJbcgLCMKNVpyc5VaiDx/PwDVv4nv/w58XXzxHA&#10;OUkFhY04QUjw0CE7UeLHO16G/Z04xNlOFg+H6TCJPY9qQLcDmuEBhTk9ZvQzgSp2MCbVKN0XNSCx&#10;gX9vsbuuLqxtWOR1C0pVbmQAC1qycSskYOznIaOTMq2kcQg8LYgEbyTGeS3gPPtpOUwT0I6HiV4+&#10;6IHkqaRjdTQZoPkjWKxBdNegVzlbmNrCFSocAMoaHcZuFHqh1lHSb5U4wF4QAXaU7WvKZq7WcrYK&#10;ekMdNvroRD5MhGhZw8ZENdIwW9BbMa/e+H4YRAem05gkqVF634ZPq/Omu7YuC+Oo5R0SMTav6Kbm&#10;Fa3+M8lokUFV+YxNmGHgFY27+vHX8z9+RqFalpoaTLWywU3timODE1nhqHw77gxu4Mdh8MYMruOE&#10;+9GRPpFiUn7AEiNfcAPXH4mBAHZC6IqosoIZIH4eDYgUI2BjVPKNA4P0y1wCoyvycmBFCmEtxFa4&#10;i/+xj1CybxzGwb/mJTajWXGrWTOCEy0p100JjlEz7EVx6KkhZnYRw8f2wPloUhiGXuCoDuuTm46Z&#10;kP6Ws2yEs2wEjS5AyNj5GRpxx6sbW39TOHoO2F1DuD0MqFwm3NiL46gh3NDVAdK7xeQ7xaM3g8lZ&#10;qGy4B14OAP+ZfGDfB2phcLgEQc8OA4gDDV0MnBjCmddC4AKXW+CEzuG+c++oGX2h25ZfsOl/PgZ9&#10;d/mF66yw6Jpoz7H3m1p07AchVvEVsHkchkA4FaJnNMONI+y1Fj30XMgIvJY+bVmGTnJsMDPSZfje&#10;bPbTdVtUXrz88vybrxCOFVJumf90Xd9p0Oi4LlYJqAU4YhxHTovHwA7tS5lQHIFzATB7kCY1787S&#10;oS7kCI1rwIGt+XRHl99CItT1Womdv3j21y/fXjS+8HaBuKsI3sJ+28yJE9sQiajwAHZ7XSDOJlWi&#10;8jg63r11+rOJxo1leFO+8OpJrsixbENpk259h10deJ5LwYtW3HVcXWMZfDvQKjZzcnEMNkMriufp&#10;SL0zC7e+Irjk39pMo0oz8SqBeRZyjnP9r0QxXGJVidGtFTmg7ZUBWu/KYDOhzorbLrxuNiiOwX2B&#10;PQcE6yFmCFZXhA2E3UBH9+tDeMF+L4Q821uut3zLNWMpG7olwE4Xqs+lj+ChpnbdXcFNo43AjjRQ&#10;FSeJY9uOl+hdaHsR5Iy0LXYiDHxNTbQ+lOes6zanuZGcpkYo/NRCn1rzsxD1W475OpTnf7yy9zdQ&#10;SwMECgAAAAAAh07iQAAAAAAAAAAAAAAAAAoAAABkcnMvbWVkaWEvUEsDBBQAAAAIAIdO4kDyL1yq&#10;nAwAAJcMAAAUAAAAZHJzL21lZGlhL2ltYWdlMS5HSUYBlwxo80dJRjg5YQABAAHwAAAAAAD///8s&#10;AAAAAAABAAFACP8AAwgcSLCgwYMIEypcyLChw4cQI0qcSLGixYsYM2rcyLGjx48cAYgcSbKkyZMo&#10;U6oEAHGlS5YHUSKU+fDlypk2c+pUiZEmSIM+GwZluLPoy5YncZr8GTPpxaEDjZZUOrXpUqtSU1Il&#10;ifSqRagCs47cOvapU6BiYaL1WhBsAJ1d09okK5Ku2rZp7fY8i1esXqxca/JlGjavYLYE5f79Opiw&#10;W7iAbx4+ahZx1MZM3caVy7ns2qqfA4f23Bd0adGnSSfG/Laz67qEY29WfdlyYdutce+dK7s3Udav&#10;XYKE7PAx68Wpg5uOrTkyz8lSd1MeDZu6Vui0by9fSLyjcdzKO9v/7b5at/Pwz32fV3weefntCsnX&#10;np78bsic0lHf510cP3bx66FnmHXjHeebfJkZSOCBwDXIX335ZZdRcwtW6J2CQmH4nn7zcaidh7nR&#10;t6GEEVZXEYUZmmfhb35VBl9C7FmH4n4vCujgiyXu5F6HJLoI4nA3/shgizJqyJ1//RF5opHxMRmi&#10;kCvyaCJFNio54pQTfSfiRjOqJ+V1RT54pX1HgoekbAhG1OWHEqI3W3oQBqhifVVm5eWdKdrZ3pB6&#10;MjYnizj+V+eWTZopZpk1hrcjm1gyCuaFf3IZZI8exRhnlBKluaaakxa16JOSYcqpoYGGGdynQJIa&#10;3Z6iarlqq2dS/+lkgZEWmqiVX366qaXMxfqlq5QuWeuETm5Ka6nCEgqjr7ZCmeygzIIK55iadkpm&#10;pgPm6Sye3Hbr7bfghivuuOSWCyi0hzZrlK7FZkstusayima7w8K7q7XqxQsrro6yqxyq8sqq6roB&#10;Z2lttQM3Ku2j51bp7619Xvpsm+7mWrC6CqcaccMK82qxn8jSCCLCyh67LaIjz+pjsBzbC2zH+F68&#10;8MMpuyycuT+9fO3MMueoL85JQlynxjcHXXPIH9OpKJ8nO3ovv6Mqq/PTQrP8q8pGU5ywzVuT3PTC&#10;8ILrpsw6v8nw1VsTHerEGa88Lcprt+yx01zHnfS7w6r9doL1Yv/N9s5Txwzy12YX3rPbOxueNcxp&#10;y1l124NbjXHigiquLcGRQ650umD7LfDjAgKcuY5kew635iJrjTTfG6O9+rKcm2w3tnkfLirNksNe&#10;d9mIy466wbX35jXmErfsO+WVRmu58Y6/evnePJv69e6eAs008WPmWzHtr4v+PPW/99t87oFDzbv0&#10;uS/vmODzms/+50UXL2nX0ZYPeo7e432q7ZCCPjzj9xsf9OxHOP7JzXTiQ98A33e305FOePWLYPC+&#10;p72+TVB/BVzc2SZHtQx+D3zHO9755Mc95zHPeihMoQpXyMIWuvCFMIyhDGdIwxra8IY4zKELwddB&#10;7DUwgR+UIA//PYhBm5ntf4CjV+votjv8XS980QsitNQHxF5d8Ic5U+L0qEfFKFqxe6VzX+MU+EAS&#10;6o6IWJybF4HnPyEe7Y2uyR8Wu6jGn2VPgBtcYxVdt8UphrGNnLNjGrenx0KmSYSEHGERF3jFPTJR&#10;agycXAWXeEbVmXCOlcOjIjF5u4+oEXfV66QGJwlHRpYMj04UZf/Q6Mgs0u+VrztkJlWZOigi0o/7&#10;CmAuS5lEWHLNZ1z8Iy9B2Utdbq5uOjyhGTepEVkm84XMNCQCKwm9Z7YybJ5UnjDJd7ClQdCXccxm&#10;7LYJRQKG85ux1CYZBfnIYaJylmYMYe8E1cNQ/i2azlymFmdH/6wxHvOXmowkNe0FNHwOEZCQtCAy&#10;vzW2XVqSm4F0Y/roOU12dk6MxryjBs0JQFYO1KIWledG9wmgdQbTodV8Yh5FKkVKHjShCPUoMBsZ&#10;tT6CcXTEjGYXZ3rTv+1UnBnl40NtaUA22tSdyaNpPYuJ1H8OFXmrhGhQm1lRkjL1qSzVaFSJSksH&#10;/tSkSrUqEkFa1bAq9Kg+LKFUL9nOieI0oBiVKVynWshUloujnJGjWu2p1b7Or6fwwypDE6nOlqKV&#10;r0Z1qz5pytP4LRKqBzxrWmuqWL9KEp6WpewQ9TrQrP71sJAdpFlfelWuLvaUbzXXWCVazm4ylqKj&#10;lWtSYcrWuv+Sc6VAdWxiWwtOxHJQoPl8LAhzu9anfvKdKMUtWBdqTdjStrK7LWNgTZvZ5tZxmkcs&#10;rDKnG9rfNhGzQuVtTCdr0HGObrijRC4F56rba7oWsF5VKWcby0+dOreptexuP+mqN+mKFr5t7Sh0&#10;L7tc/Ia3tPolZnb/VVSq9jaPeJ3vbRe8v646eLymNLB742rcsgL4oh/Ga3npet0H25W7ZH3wiDX8&#10;Ue1+tr6sTTB4Awxh4IrVxRduL4hZfOL1SZa6B+7vUpfaX1IWF8g0FvKNfyzjkX6XjLcksYdrK+Hz&#10;ntSpAvatNGM72So398tgDrOYx0zmMpv5zGhOs5rXzOY2u/n/zXCOs5znTOc623m/L6Wwju1b4A4/&#10;+Z5Xjuxzs6zfEpOWvi7TM4z9Keg9kxbJOzbifRfNX/b6mbk+le+MW4zL9VoYzwO27WxBq2gb0RG7&#10;m/7qVgu95EGd+rXpTW370slkbMY6nsSFtIhtrOIYq3q7B+azp7FcY0YH29ep3rShPbprY2+4wrj+&#10;dE6RLWvh8hrD/m2llzO77FA3W8omnnRJT8tLJPaYxiu+NGgbSm51U5rZ73W0syMt3qFN2NKEPje9&#10;iw1u1BK72/nGN4L1DfCBJ3vUXb6tuefZ54B/OqTiVm67tZxibKe73qEutcS5TVguE3rhD0e1k/3N&#10;cQ7rWuTD/36sZ70bYoEWmeGzLne8j7zx+Eob5tw18sdnjvGU3rq6uW5ytFe98n1rnOAoB/Zeeajg&#10;eyd32n9GcaD/y+KLG7zhoUMceid+da43HevslnrUqS7Ypzv93+gys9W37UCI3znTZr/5yGX79uh+&#10;fehi/3Dd4e71Bmu25jj8NpW1blXOCj7hCN85gxMP+C3T2uKEfDnYcfx3Ivv98FqWPNqhhmhWzxvq&#10;0P7iulut46MrvPCXp7zhKX/cuZed2K9+fOkPDvuIV1ze2I796D3ueZIfm/dWt3zIzcv3kh9a4Ksf&#10;u7iCP3V0u1zgre8W85W/b2Fbe/F3vXaekZ966us8r2cHOv+U1St+QPO+8yDvu+sVX2krn5/wJu96&#10;+VVOfk6av+XA73i/d597v5u+2i/mc7W3ftlWfc3neLOHTgk4gIZFXo/GbzIHa/fHfxTogA+offz0&#10;fUNGehZ4gfuXgSrldilnczH3evQnd3gXXSKodJ2lacllfSSYXysYgzTIOlVHbcUXgI2nb2y3dKDH&#10;Y7SXc8qWdPbHgndnfLhHd0YYfjrIg0z4bEmYcUFIdpDWg6KmflwHgzlGc1ZohcKHhSeYgj6Ic3Y3&#10;hc73fgQYYWQ4hgBIYA0ogEuIdUX3azX4d3R4hvgnex24gHE4gmzoh5zme27YaO8GgowXZbU1g3h4&#10;gxI4f3j/gnkFqIgGOG+SKIlI12mOuHTpl4l2iDOQuIk6CIratoau1H4lSHGqV38sx4d1qHtS6H6D&#10;toOqGIisOIhliIIyGGO3Z4ibl4fw9nkhqItTxovX54vehno/V4Rtd4C7yH5KqIyjGHcTKGm4SIWy&#10;mIy2mI1XSIiJ5n+n14jQuI3WiH5p540vSITRKH3CGFFMNodB14xCJ3qFiIrtOItKhobZx44uhYwM&#10;KI9w2IqDpY+JyI9h6I/XKIYIeE5vKH9BZnsH6ITnyHmzqIZp+JCk+IOI14/WuIGPpnksGHbLSIms&#10;54HgCEMFp4GpWI3pqEMnGYzE14cwmUMtqYAZGZPaaF36//eMq0iMAImNlWeREamHzriHJtiQjLd1&#10;HzmSvSaQRZlk7wiUWUiQvxeLGFmF0OdNgBhcG5mTTWl0Qfd9XeiQIpl/wNiGpQiER4mDnFh0lYiO&#10;LaiSP8mUDjeNc5lfYGmOdAmBXfl/COmK9Nh/cKmFk7iPJfmWvciIVEmKHBl64XiJhAmYffmEtKiQ&#10;a6mYXDmUdXmLCGlrkWmWMwmGU4mYtWiYefeLkDeWH/iXEWiMJ/eSdfiZSOhuGSab8diY9jiE8XeE&#10;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&#10;AAoACgBSAgAAJhgAAAAA&#10;">
											<o:lock v:ext="edit" aspectratio="f" />
											<v:shape id="_x0000_s1026" o:spid="_x0000_s1026"
												style="position:absolute;left:0;top:0;height:3096260;width:1965325;"
												filled="f" stroked="t" coordsize="21600,21600"
												o:gfxdata="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&#10;LkdJRlBLBQYAAAAACgAKAFICAAAxGAAAAAA=&#10;">
												<v:fill on="f" focussize="0,0" />
												<v:stroke weight="1.5pt" color="#2E75B6" joinstyle="round" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="t" />
											</v:shape>
											<v:shape id="textbox${c_index}express" o:spid="_x0000_s1026"
												o:spt="202" type="#_x0000_t202"
												style="position:absolute;left:98425;top:750570;height:564515;width:1763395;"
												fillcolor="#FFFFFF [3201]" filled="t" stroked="f" coordsize="21600,21600"
												o:gfxdata="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&#10;">
												<v:fill on="t" focussize="0,0" />
												<v:stroke on="f" weight="0.5pt" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="f" />
												<v:textbox>
													<w:txbxContent>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="140" w:lineRule="exact" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t>工作证使用说明</w:t>
															</w:r>
														</w:p>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="140" w:lineRule="exact" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:rFonts w:hint="default" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t xml:space="preserve">· </w:t>
															</w:r>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t>二维码作为您在社区活动电子签到和积分管理的唯一凭证，请妥善保护</w:t>
															</w:r>
														</w:p>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="140" w:lineRule="exact" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:rFonts w:hint="default" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t xml:space="preserve">· </w:t>
															</w:r>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="Microsoft YaHei UI"
																		w:hAnsi="Microsoft YaHei UI" w:eastAsia="Microsoft YaHei UI"
																		w:cs="Microsoft YaHei UI" />
																	<w:b w:val="0" />
																	<w:bCs w:val="0" />
																	<w:color w:val="A6A6A6" w:themeColor="background1"
																		w:themeShade="A6" />
																	<w:sz w:val="11" />
																	<w:szCs w:val="11" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t>工作证有效期截止于19年10月，之后将在志愿者回馈日统一收回，请您妥善保存。</w:t>
															</w:r>
														</w:p>
													</w:txbxContent>
												</v:textbox>
											</v:shape>
											<v:shape id="img${c_index}" o:spid="_x0000_s1026"
												o:spt="75" alt="267a1b2a-8d59-4f15-971f-f9bbebd944c1" type="#_x0000_t75"
												style="position:absolute;left:657225;top:1387475;height:614680;width:614680;"
												filled="f" o:preferrelative="t" stroked="t" coordsize="21600,21600"
												o:gfxdata="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&#10;">
												<v:fill on="f" focussize="0,0" />
												<v:stroke weight="2.25pt" color="#1E768C [3204]"
													joinstyle="round" />
												<v:imagedata r:id="rId${c_index}Gif" o:title="" />
												<o:lock v:ext="edit" aspectratio="t" />
											</v:shape>
											<v:rect id="rectangle${c_index}blue" o:spid="_x0000_s1026"
												o:spt="1"
												style="position:absolute;left:0;top:0;height:659765;width:1509395;v-text-anchor:middle;"
												fillcolor="#227A8F [2404]" filled="t" stroked="t" coordsize="21600,21600"
												o:gfxdata="UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAEAAAAZHJzL1BLAwQUAAAACACHTuJA19Y0vdgAAAAF&#10;AQAADwAAAGRycy9kb3ducmV2LnhtbE2PQUvDQBCF74L/YRnBS2l3a7UmMZuCSk8KaiuKt2l2TILZ&#10;2ZDdtsm/d/Wil4HHe7z3Tb4abCsO1PvGsYb5TIEgLp1puNLwul1PExA+IBtsHZOGkTysitOTHDPj&#10;jvxCh02oRCxhn6GGOoQuk9KXNVn0M9cRR+/T9RZDlH0lTY/HWG5beaHUUlpsOC7U2NFdTeXXZm81&#10;TBbrj/v0eaTJ+DC83cr35ClJH7U+P5urGxCBhvAXhh/8iA5FZNq5PRsvWg3xkfB7o7dQ6RWInYbL&#10;5HoJssjlf/riG1BLAwQUAAAACACHTuJAfyBMcnYCAADqBAAADgAAAGRycy9lMm9Eb2MueG1srVTN&#10;bhMxEL4j8Q6W73Q3S5NNom6qKCUIqdBKBXF2vHbWkv+wnWzKyyD1xkPwOIjXYGxv0xaQkBAX74xn&#10;/M3MNzN7dn5QEu2Z88LoBo9OSoyYpqYVetvgD+/XL6YY+UB0S6TRrMG3zOPzxfNnZ72ds8p0RrbM&#10;IQDRft7bBnch2HlReNoxRfyJsUyDkRunSADVbYvWkR7QlSyqspwUvXGtdYYy7+H2IhvxIuFzzmi4&#10;4tyzgGSDIbeQTpfOTTyLxRmZbx2xnaBDGuQfslBEaAh6hLoggaCdE79BKUGd8YaHE2pUYTgXlKUa&#10;oJpR+Us1Nx2xLNUC5Hh7pMn/P1j6bn/tkGgbXEGnNFHQox9fvn7/dofqSE5v/Rx8buy1GzQPYqz0&#10;wJ2KX6gBHRKht0dC2SEgCpejcTl7ORtjRME2Gc/qyTiCFg+vrfPhNTMKRaHBDhqWeCT7Sx+y671L&#10;DOaNFO1aSJkUt92spEN7As2tqno5Xae3cqfemjZf1+OyTF2GmD77p/hPgKRGPSRb1eCKKIEx5JIE&#10;EJUFYrzeYkTkFuabBpciPHk9wOZ4o1f1ZLrKTh1pWb6NSfw1i1jnBfFdfpJC5PFUIsCOSKEaPI1A&#10;90hSQymxQbklUdqY9hb66UwedG/pWgDsJfHhmjiYbCgQtjVcwcGlgarNIGHUGff5T/fRHwYOrBj1&#10;sCnAyKcdcQwj+UbDKM5Gp6dxtZJyOq4rUNxjy+axRe/UykC/RvBfsDSJ0T/Ie5E7oz7CUi9jVDAR&#10;TSF25n5QViFvMPwWKFsukxuskyXhUt9YGsHjfGiz3AXDRZqjB3YG0mCh0iQMyx839rGevB5+UYuf&#10;UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAGAAAAX3JlbHMvUEsDBBQAAAAIAIdO4kCKFGY80QAA&#10;AJQBAAALAAAAX3JlbHMvLnJlbHOlkMFqwzAMhu+DvYPRfXGawxijTi+j0GvpHsDYimMaW0Yy2fr2&#10;8w6DZfS2o36h7xP//vCZFrUiS6RsYNf1oDA78jEHA++X49MLKKk2e7tQRgM3FDiMjw/7My62tiOZ&#10;YxHVKFkMzLWWV63FzZisdFQwt81EnGxtIwddrLvagHro+2fNvxkwbpjq5A3wyQ+gLrfSzH/YKTom&#10;oal2jpKmaYruHlUHtmWO7sg24Ru5RrMcsBrwLBoHalnXfgR9X7/7p97TRz7jutV+h4zrj1dvuhy/&#10;AFBLAwQUAAAACACHTuJAfublIPcAAADhAQAAEwAAAFtDb250ZW50X1R5cGVzXS54bWyVkUFOwzAQ&#10;RfdI3MHyFiVOu0AIJemCtEtAqBxgZE8Si2RseUxob4+TthtEkVjaM/+/J7vcHMZBTBjYOqrkKi+k&#10;QNLOWOoq+b7fZQ9ScAQyMDjCSh6R5aa+vSn3R48sUpq4kn2M/lEp1j2OwLnzSGnSujBCTMfQKQ/6&#10;AzpU66K4V9pRRIpZnDtkXTbYwucQxfaQrk8mAQeW4um0OLMqCd4PVkNMpmoi84OSnQl5Si473FvP&#10;d0lDql8J8+Q64Jx7SU8TrEHxCiE+w5g0lAmsjPuigFP+d8lsOXLm2tZqzJvATYq94XSxutaOa9c4&#10;/d/y7ZK6dKvlg+pvUEsBAhQAFAAAAAgAh07iQH7m5SD3AAAA4QEAABMAAAAAAAAAAQAgAAAA5wQA&#10;AFtDb250ZW50X1R5cGVzXS54bWxQSwECFAAKAAAAAACHTuJAAAAAAAAAAAAAAAAABgAAAAAAAAAA&#10;ABAAAADJAwAAX3JlbHMvUEsBAhQAFAAAAAgAh07iQIoUZjzRAAAAlAEAAAsAAAAAAAAAAQAgAAAA&#10;7QMAAF9yZWxzLy5yZWxzUEsBAhQACgAAAAAAh07iQAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAQAAAA&#10;AAAAAGRycy9QSwECFAAUAAAACACHTuJA19Y0vdgAAAAFAQAADwAAAAAAAAABACAAAAAiAAAAZHJz&#10;L2Rvd25yZXYueG1sUEsBAhQAFAAAAAgAh07iQH8gTHJ2AgAA6gQAAA4AAAAAAAAAAQAgAAAAJwEA&#10;AGRycy9lMm9Eb2MueG1sUEsFBgAAAAAGAAYAWQEAAA8GAAAAAA==&#10;">
												<v:fill on="t" focussize="0,0" />
												<v:stroke weight="1pt" color="#1E768C [3204]"
													miterlimit="8" joinstyle="miter" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="f" />
											</v:rect>
											<v:shape id="textbox${c_index}title" o:spid="_x0000_s1026"
												o:spt="202" type="#_x0000_t202"
												style="position:absolute;left:0;top:148974;height:377462;width:1111046;"
												filled="f" stroked="f" coordsize="21600,21600"
												o:gfxdata="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&#10;AAYAWQEAAL4FAAAAAA==&#10;">
												<v:fill on="f" focussize="0,0" />
												<v:stroke on="f" weight="0.5pt" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="f" />
												<v:textbox>
													<w:txbxContent>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="300" w:lineRule="exact" />
																<w:jc w:val="left" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																		w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																	<w:color w:val="FFFFFF" w:themeColor="background1" />
																	<w:sz w:val="30" />
																	<w:szCs w:val="30" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	<w14:glow w14:rad="0">
																		<w14:srgbClr w14:val="000000" />
																	</w14:glow>
																	<w14:shadow w14:blurRad="38100" w14:dist="19050"
																		w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																		w14:kx="0" w14:ky="0" w14:algn="tl">
																		<w14:schemeClr w14:val="dk1">
																			<w14:alpha w14:val="60000" />
																		</w14:schemeClr>
																	</w14:shadow>
																	<w14:reflection w14:blurRad="0" w14:stA="0"
																		w14:stPos="0" w14:endA="0" w14:endPos="0" w14:dist="0"
																		w14:dir="0" w14:fadeDir="0" w14:sx="0" w14:sy="0"
																		w14:kx="0" w14:ky="0" w14:algn="none" />
																	<w14:textFill>
																		<w14:solidFill>
																			<w14:schemeClr w14:val="bg1" />
																		</w14:solidFill>
																	</w14:textFill>
																	<w14:props3d w14:extrusionH="0"
																		w14:contourW="0" w14:prstMaterial="clear" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																		w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																	<w:color w:val="FFFFFF" w:themeColor="background1" />
																	<w:sz w:val="30" />
																	<w:szCs w:val="30" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	<w14:glow w14:rad="0">
																		<w14:srgbClr w14:val="000000" />
																	</w14:glow>
																	<w14:shadow w14:blurRad="38100" w14:dist="19050"
																		w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																		w14:kx="0" w14:ky="0" w14:algn="tl">
																		<w14:schemeClr w14:val="dk1">
																			<w14:alpha w14:val="60000" />
																		</w14:schemeClr>
																	</w14:shadow>
																	<w14:reflection w14:blurRad="0" w14:stA="0"
																		w14:stPos="0" w14:endA="0" w14:endPos="0" w14:dist="0"
																		w14:dir="0" w14:fadeDir="0" w14:sx="0" w14:sy="0"
																		w14:kx="0" w14:ky="0" w14:algn="none" />
																	<w14:textFill>
																		<w14:solidFill>
																			<w14:schemeClr w14:val="bg1" />
																		</w14:solidFill>
																	</w14:textFill>
																	<w14:props3d w14:extrusionH="0"
																		w14:contourW="0" w14:prstMaterial="clear" />
																</w:rPr>
																<w:t>工作证</w:t>
															</w:r>
														</w:p>
													</w:txbxContent>
												</v:textbox>
											</v:shape>
											<v:shape id="textbox${c_index}comefrome" o:spid="_x0000_s1026"
												o:spt="202" type="#_x0000_t202"
												style="position:absolute;left:422275;top:411480;height:222250;width:1149985;"
												filled="f" stroked="f" coordsize="21600,21600"
												o:gfxdata="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&#10;AAAGAAYAWQEAAMEFAAAAAA==&#10;">
												<v:fill on="f" focussize="0,0" />
												<v:stroke on="f" weight="0.5pt" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="f" />
												<v:textbox>
													<w:txbxContent>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="200" w:lineRule="exact" />
																<w:jc w:val="left" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																		w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																	<w:color w:val="FFFFFF" w:themeColor="background1" />
																	<w:sz w:val="15" />
																	<w:szCs w:val="15" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	<w14:glow w14:rad="0">
																		<w14:srgbClr w14:val="000000" />
																	</w14:glow>
																	<w14:shadow w14:blurRad="38100" w14:dist="19050"
																		w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																		w14:kx="0" w14:ky="0" w14:algn="tl">
																		<w14:schemeClr w14:val="dk1">
																			<w14:alpha w14:val="60000" />
																		</w14:schemeClr>
																	</w14:shadow>
																	<w14:reflection w14:blurRad="0" w14:stA="0"
																		w14:stPos="0" w14:endA="0" w14:endPos="0" w14:dist="0"
																		w14:dir="0" w14:fadeDir="0" w14:sx="0" w14:sy="0"
																		w14:kx="0" w14:ky="0" w14:algn="none" />
																	<w14:textFill>
																		<w14:solidFill>
																			<w14:schemeClr w14:val="bg1" />
																		</w14:solidFill>
																	</w14:textFill>
																	<w14:props3d w14:extrusionH="0"
																		w14:contourW="0" w14:prstMaterial="clear" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																		w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																	<w:color w:val="FFFFFF" w:themeColor="background1" />
																	<w:sz w:val="15" />
																	<w:szCs w:val="15" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	<w14:glow w14:rad="0">
																		<w14:srgbClr w14:val="000000" />
																	</w14:glow>
																	<w14:shadow w14:blurRad="38100" w14:dist="19050"
																		w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																		w14:kx="0" w14:ky="0" w14:algn="tl">
																		<w14:schemeClr w14:val="dk1">
																			<w14:alpha w14:val="60000" />
																		</w14:schemeClr>
																	</w14:shadow>
																	<w14:reflection w14:blurRad="0" w14:stA="0"
																		w14:stPos="0" w14:endA="0" w14:endPos="0" w14:dist="0"
																		w14:dir="0" w14:fadeDir="0" w14:sx="0" w14:sy="0"
																		w14:kx="0" w14:ky="0" w14:algn="none" />
																	<w14:textFill>
																		<w14:solidFill>
																			<w14:schemeClr w14:val="bg1" />
																		</w14:solidFill>
																	</w14:textFill>
																	<w14:props3d w14:extrusionH="0"
																		w14:contourW="0" w14:prstMaterial="clear" />
																</w:rPr>
																<w:t>${(c.minusFirstName)!''}${(c.zeroName)!''}</w:t>
															</w:r>
														</w:p>
													</w:txbxContent>
												</v:textbox>
											</v:shape>
											<v:rect id="rectangle${c_index}lightblue" o:spid="_x0000_s1026"
												o:spt="1"
												style="position:absolute;left:1557655;top:0;height:662940;width:407670;v-text-anchor:middle;"
												fillcolor="#2DA2BF [3204]" filled="t" stroked="t" coordsize="21600,21600"
												o:gfxdata="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&#10;">
												<v:fill on="t" focussize="0,0" />
												<v:stroke weight="1pt" color="#1E768C [3204]"
													miterlimit="8" joinstyle="miter" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="f" />
											</v:rect>
											<v:shape id="textbox${c_index}Index" o:spid="_x0000_s1026"
												o:spt="202" type="#_x0000_t202"
												style="position:absolute;left:1567180;top:177800;height:274320;width:398145;"
												filled="f" stroked="f" coordsize="21600,21600"
												o:gfxdata="UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAEAAAAZHJzL1BLAwQUAAAACACHTuJAkG9MS9gAAAAF&#10;AQAADwAAAGRycy9kb3ducmV2LnhtbE2PzU7DMBCE70i8g7VI3KjdQksIcSoUqUJCcGjphdsm3iYR&#10;9jrE7g88PYYLXFYazWjm22J5clYcaAy9Zw3TiQJB3HjTc6th+7q6ykCEiGzQeiYNnxRgWZ6fFZgb&#10;f+Q1HTaxFamEQ44auhiHXMrQdOQwTPxAnLydHx3GJMdWmhGPqdxZOVNqIR32nBY6HKjqqHnf7J2G&#10;p2r1gut65rIvWz0+7x6Gj+3bXOvLi6m6BxHpFP/C8IOf0KFMTLXfswnCakiPxN+bvGt1NwdRa7jJ&#10;bhcgy0L+py+/AVBLAwQUAAAACACHTuJA0z9SeioCAAAkBAAADgAAAGRycy9lMm9Eb2MueG1srVPN&#10;jtMwEL4j8Q6W7zRJ/7dquiq7KkJasSsVxNl17DZS7DG226Q8ALwBJy7cea4+B2Mn7VbACXFxJv7G&#10;8/PNN/PbRlXkIKwrQec066WUCM2hKPU2px/er15NKXGe6YJVoEVOj8LR28XLF/PazEQfdlAVwhIM&#10;ot2sNjndeW9mSeL4TijmemCERlCCVczjr90mhWU1RldV0k/TcVKDLYwFLpzD2/sWpIsYX0rB/aOU&#10;TnhS5RRr8/G08dyEM1nM2WxrmdmVvCuD/UMVipUak15C3TPPyN6Wf4RSJbfgQPoeB5WAlCUXsQfs&#10;Jkt/62a9Y0bEXpAcZy40uf8Xlr87PFlSFjkd9CnRTOGMTt++nr7/PP34QrJRIKg2boZ+a4OevnkN&#10;DQ76fO/wMvTdSKvCFzsiAR+NJ9kUCT+iPZlM045p0XjCER/cTLPhiBKOeH8yHPQjnjzHMdb5NwIU&#10;CUZOLQ4y8ssOD85jTeh6dglpNazKqorDrDSpczoejNL44ILgi0rjw9BNW3WwfLNpuhY3UByxQwut&#10;SJzhqxKTPzDnn5hFVWA7qHT/iIesAJNAZ1GyA/v5b/fBH4eFKCU1qiyn7tOeWUFJ9VbjGG+y4TDI&#10;Mv4MRxPkgdhrZHON6L26AxRyhjtleDSDv6/OprSgPuJCLENWhJjmmDun/mze+Vb7uFBcLJfRCYVo&#10;mH/Qa8ND6JbO5d6DLCPTgaaWm449lGIcQLc2QevX/9HrebkXvwBQSwMECgAAAAAAh07iQAAAAAAA&#10;AAAAAAAAAAYAAABfcmVscy9QSwMEFAAAAAgAh07iQIoUZjzRAAAAlAEAAAsAAABfcmVscy8ucmVs&#10;c6WQwWrDMAyG74O9g9F9cZrDGKNOL6PQa+kewNiKYxpbRjLZ+vbzDoNl9LajfqHvE//+8JkWtSJL&#10;pGxg1/WgMDvyMQcD75fj0wsoqTZ7u1BGAzcUOIyPD/szLra2I5ljEdUoWQzMtZZXrcXNmKx0VDC3&#10;zUScbG0jB12su9qAeuj7Z82/GTBumOrkDfDJD6Aut9LMf9gpOiahqXaOkqZpiu4eVQe2ZY7uyDbh&#10;G7lGsxywGvAsGgdqWdd+BH1fv/un3tNHPuO61X6HjOuPV2+6HL8AUEsDBBQAAAAIAIdO4kB+5uUg&#10;9wAAAOEBAAATAAAAW0NvbnRlbnRfVHlwZXNdLnhtbJWRQU7DMBBF90jcwfIWJU67QAgl6YK0S0Co&#10;HGBkTxKLZGx5TGhvj5O2G0SRWNoz/78nu9wcxkFMGNg6quQqL6RA0s5Y6ir5vt9lD1JwBDIwOMJK&#10;HpHlpr69KfdHjyxSmriSfYz+USnWPY7AufNIadK6MEJMx9ApD/oDOlTrorhX2lFEilmcO2RdNtjC&#10;5xDF9pCuTyYBB5bi6bQ4syoJ3g9WQ0ymaiLzg5KdCXlKLjvcW893SUOqXwnz5DrgnHtJTxOsQfEK&#10;IT7DmDSUCayM+6KAU/53yWw5cuba1mrMm8BNir3hdLG61o5r1zj93/Ltkrp0q+WD6m9QSwECFAAU&#10;AAAACACHTuJAfublIPcAAADhAQAAEwAAAAAAAAABACAAAACbBAAAW0NvbnRlbnRfVHlwZXNdLnht&#10;bFBLAQIUAAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAGAAAAAAAAAAAAEAAAAH0DAABfcmVscy9QSwEC&#10;FAAUAAAACACHTuJAihRmPNEAAACUAQAACwAAAAAAAAABACAAAAChAwAAX3JlbHMvLnJlbHNQSwEC&#10;FAAKAAAAAACHTuJAAAAAAAAAAAAAAAAABAAAAAAAAAAAABAAAAAAAAAAZHJzL1BLAQIUABQAAAAI&#10;AIdO4kCQb0xL2AAAAAUBAAAPAAAAAAAAAAEAIAAAACIAAABkcnMvZG93bnJldi54bWxQSwECFAAU&#10;AAAACACHTuJA0z9SeioCAAAkBAAADgAAAAAAAAABACAAAAAnAQAAZHJzL2Uyb0RvYy54bWxQSwUG&#10;AAAAAAYABgBZAQAAwwUAAAAA&#10;">
												<v:fill on="f" focussize="0,0" />
												<v:stroke on="f" weight="0.5pt" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="f" />
												<v:textbox>
													<w:txbxContent>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="320" w:lineRule="exact" />
																<w:jc w:val="left" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																		w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																	<w:color w:val="FFFFFF" w:themeColor="background1" />
																	<w:w w:val="80" />
																	<w:sz w:val="32" />
																	<w:szCs w:val="32" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	<w14:glow w14:rad="0">
																		<w14:srgbClr w14:val="000000" />
																	</w14:glow>
																	<w14:shadow w14:blurRad="38100" w14:dist="19050"
																		w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																		w14:kx="0" w14:ky="0" w14:algn="tl">
																		<w14:schemeClr w14:val="dk1">
																			<w14:alpha w14:val="60000" />
																		</w14:schemeClr>
																	</w14:shadow>
																	<w14:reflection w14:blurRad="0" w14:stA="0"
																		w14:stPos="0" w14:endA="0" w14:endPos="0" w14:dist="0"
																		w14:dir="0" w14:fadeDir="0" w14:sx="0" w14:sy="0"
																		w14:kx="0" w14:ky="0" w14:algn="none" />
																	<w14:textFill>
																		<w14:solidFill>
																			<w14:schemeClr w14:val="bg1" />
																		</w14:solidFill>
																	</w14:textFill>
																	<w14:props3d w14:extrusionH="0"
																		w14:contourW="0" w14:prstMaterial="clear" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																		w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																	<w:color w:val="FFFFFF" w:themeColor="background1" />
																	<w:w w:val="80" />
																	<w:sz w:val="32" />
																	<w:szCs w:val="32" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	<w14:glow w14:rad="0">
																		<w14:srgbClr w14:val="000000" />
																	</w14:glow>
																	<w14:shadow w14:blurRad="38100" w14:dist="19050"
																		w14:dir="2700000" w14:sx="100000" w14:sy="100000"
																		w14:kx="0" w14:ky="0" w14:algn="tl">
																		<w14:schemeClr w14:val="dk1">
																			<w14:alpha w14:val="60000" />
																		</w14:schemeClr>
																	</w14:shadow>
																	<w14:reflection w14:blurRad="0" w14:stA="0"
																		w14:stPos="0" w14:endA="0" w14:endPos="0" w14:dist="0"
																		w14:dir="0" w14:fadeDir="0" w14:sx="0" w14:sy="0"
																		w14:kx="0" w14:ky="0" w14:algn="none" />
																	<w14:textFill>
																		<w14:solidFill>
																			<w14:schemeClr w14:val="bg1" />
																		</w14:solidFill>
																	</w14:textFill>
																	<w14:props3d w14:extrusionH="0"
																		w14:contourW="0" w14:prstMaterial="clear" />
																</w:rPr>
																<w:t>${(c.indexNum)!''}</w:t>
															</w:r>
														</w:p>
													</w:txbxContent>
												</v:textbox>
											</v:shape>
											<v:group id="combine${c_index}" o:spid="_x0000_s1026"
												o:spt="203"
												style="position:absolute;left:335280;top:2331720;height:607060;width:1198245;"
												coordorigin="181,4962" coordsize="3057,1604"
												o:gfxdata="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&#10;AAAAAA==&#10;">
												<o:lock v:ext="edit" aspectratio="f" />
												<v:roundrect id="angleRectangle${c_index}"
													o:spid="_x0000_s1026" o:spt="2"
													style="position:absolute;left:330;top:4962;height:1605;width:2909;v-text-anchor:middle;"
													fillcolor="#227A8F" filled="t" stroked="t" coordsize="21600,21600"
													arcsize="0.166666666666667"
													o:gfxdata="UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAEAAAAZHJzL1BLAwQUAAAACACHTuJADmpOM70AAADb&#10;AAAADwAAAGRycy9kb3ducmV2LnhtbEWP3WoCMRSE7wt9h3AK3tXEKqWuRi+EpSptwZ8HOGyOu4ub&#10;kyWJ7vr2piB4OczMN8x82dtGXMmH2rGG0VCBIC6cqbnUcDzk718gQkQ22DgmDTcKsFy8vswxM67j&#10;HV33sRQJwiFDDVWMbSZlKCqyGIauJU7eyXmLMUlfSuOxS3DbyA+lPqXFmtNChS2tKirO+4tNlPB3&#10;2ezUrViv+u9u639+bZ5PtR68jdQMRKQ+PsOP9tpoGE/g/0v6AXJxB1BLAwQUAAAACACHTuJAMy8F&#10;njsAAAA5AAAAEAAAAGRycy9zaGFwZXhtbC54bWyzsa/IzVEoSy0qzszPs1Uy1DNQUkjNS85PycxL&#10;t1UKDXHTtVBSKC5JzEtJzMnPS7VVqkwtVrK34+UCAFBLAwQKAAAAAACHTuJAAAAAAAAAAAAAAAAA&#10;BgAAAF9yZWxzL1BLAwQUAAAACACHTuJA1VwmKMwAAACPAQAACwAAAF9yZWxzLy5yZWxzpZCxagMx&#10;DIb3QN/BaO/5kqGUEF+2QtaQQldh6+5MzpaxzDV5+7iUQi9ky6BBv9D3Ce32lzCpmbJ4jgbWTQuK&#10;omXn42Dg8/Tx+g5KCkaHE0cycCWBffey2h1pwlKXZPRJVKVEMTCWkrZaix0poDScKNZJzzlgqW0e&#10;dEJ7xoH0pm3fdP7PgG7BVAdnIB/cBtTpmqr5jh28zSzcl8Zy0Nz33j6iahkx0VeYKgbzQMWAy/Kb&#10;1tOaWqAfm9dPmh1/xyPNS/FPmGn+8+rFG7sbUEsDBBQAAAAIAIdO4kBa4xFm9wAAAOIBAAATAAAA&#10;W0NvbnRlbnRfVHlwZXNdLnhtbJWRTU/EIBCG7yb+BzJX01I9GGNK92D1qEbXHzCBaUu2BcJg3f33&#10;0v24GNfEI8y8z/sE6tV2GsVMka13Cq7LCgQ57Y11vYKP9VNxB4ITOoOjd6RgRwyr5vKiXu8Cschp&#10;xwqGlMK9lKwHmpBLH8jlSefjhCkfYy8D6g32JG+q6lZq7xK5VKSFAU3dUoefYxKP23x9MIk0MoiH&#10;w+LSpQBDGK3GlE3l7MyPluLYUObkfocHG/gqa4D8tWGZnC845l7y00RrSLxiTM84ZQ1pIkvjv1yk&#10;ufwbslhOXPius5rKNnKbY280n6zO0XnAQBn9X/z7kjvB5f6Hmm9QSwECFAAUAAAACACHTuJAWuMR&#10;ZvcAAADiAQAAEwAAAAAAAAABACAAAACOAgAAW0NvbnRlbnRfVHlwZXNdLnhtbFBLAQIUAAoAAAAA&#10;AIdO4kAAAAAAAAAAAAAAAAAGAAAAAAAAAAAAEAAAAHUBAABfcmVscy9QSwECFAAUAAAACACHTuJA&#10;1VwmKMwAAACPAQAACwAAAAAAAAABACAAAACZAQAAX3JlbHMvLnJlbHNQSwECFAAKAAAAAACHTuJA&#10;AAAAAAAAAAAAAAAABAAAAAAAAAAAABAAAAAAAAAAZHJzL1BLAQIUABQAAAAIAIdO4kAOak4zvQAA&#10;ANsAAAAPAAAAAAAAAAEAIAAAACIAAABkcnMvZG93bnJldi54bWxQSwECFAAUAAAACACHTuJAMy8F&#10;njsAAAA5AAAAEAAAAAAAAAABACAAAAAMAQAAZHJzL3NoYXBleG1sLnhtbFBLBQYAAAAABgAGAFsB&#10;AAC2AwAAAAA=&#10;">
													<v:fill on="t" focussize="0,0" />
													<v:stroke weight="1pt" color="#227A8F [3204]"
														miterlimit="8" joinstyle="miter" />
													<v:imagedata o:title="" />
													<o:lock v:ext="edit" aspectratio="f" />
												</v:roundrect>
												<v:shape id="textbox${c_index}Rowname" o:spid="_x0000_s1026"
													o:spt="202" type="#_x0000_t202"
													style="position:absolute;left:181;top:5067;height:1448;width:991;"
													filled="f" stroked="f" coordsize="21600,21600"
													o:gfxdata="UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAEAAAAZHJzL1BLAwQUAAAACACHTuJApl4olL4AAADb&#10;AAAADwAAAGRycy9kb3ducmV2LnhtbEWPzWsCMRTE70L/h/AK3txEy0q7NXoo1Hrx4EcL3h6btx90&#10;87Js4q7+90YQPA4z8xtmsbrYRvTU+dqxhmmiQBDnztRcajgevifvIHxANtg4Jg1X8rBavowWmBk3&#10;8I76fShFhLDPUEMVQptJ6fOKLPrEtcTRK1xnMUTZldJ0OES4beRMqbm0WHNcqLClr4ry//3ZakhP&#10;xZqHa/r3W6T2tJnV259efWg9fp2qTxCBLuEZfrQ3RsNbCvcv8QfI5Q1QSwMEFAAAAAgAh07iQDMv&#10;BZ47AAAAOQAAABAAAABkcnMvc2hhcGV4bWwueG1ss7GvyM1RKEstKs7Mz7NVMtQzUFJIzUvOT8nM&#10;S7dVCg1x07VQUiguScxLSczJz0u1VapMLVayt+PlAgBQSwMECgAAAAAAh07iQAAAAAAAAAAAAAAA&#10;AAYAAABfcmVscy9QSwMEFAAAAAgAh07iQNVcJijMAAAAjwEAAAsAAABfcmVscy8ucmVsc6WQsWoD&#10;MQyG90DfwWjv+ZKhlBBftkLWkEJXYevuTM6Wscw1efu4lEIvZMugQb/Q9wnt9pcwqZmyeI4G1k0L&#10;iqJl5+Ng4PP08foOSgpGhxNHMnAlgX33stodacJSl2T0SVSlRDEwlpK2WosdKaA0nCjWSc85YKlt&#10;HnRCe8aB9KZt33T+z4BuwVQHZyAf3AbU6Zqq+Y4dvM0s3JfGctDc994+omoZMdFXmCoG80DFgMvy&#10;m9bTmlqgH5vXT5odf8cjzUvxT5hp/vPqxRu7G1BLAwQUAAAACACHTuJAWuMRZvcAAADiAQAAEwAA&#10;AFtDb250ZW50X1R5cGVzXS54bWyVkU1PxCAQhu8m/gcyV9NSPRhjSvdg9ahG1x8wgWlLtgXCYN39&#10;99L9uBjXxCPMvM/7BOrVdhrFTJGtdwquywoEOe2Ndb2Cj/VTcQeCEzqDo3ekYEcMq+byol7vArHI&#10;accKhpTCvZSsB5qQSx/I5Unn44QpH2MvA+oN9iRvqupWau8SuVSkhQFN3VKHn2MSj9t8fTCJNDKI&#10;h8Pi0qUAQxitxpRN5ezMj5bi2FDm5H6HBxv4KmuA/LVhmZwvOOZe8tNEa0i8YkzPOGUNaSJL479c&#10;pLn8G7JYTlz4rrOayjZym2NvNJ+sztF5wEAZ/V/8+5I7weX+h5pvUEsBAhQAFAAAAAgAh07iQFrj&#10;EWb3AAAA4gEAABMAAAAAAAAAAQAgAAAAjwIAAFtDb250ZW50X1R5cGVzXS54bWxQSwECFAAKAAAA&#10;AACHTuJAAAAAAAAAAAAAAAAABgAAAAAAAAAAABAAAAB2AQAAX3JlbHMvUEsBAhQAFAAAAAgAh07i&#10;QNVcJijMAAAAjwEAAAsAAAAAAAAAAQAgAAAAmgEAAF9yZWxzLy5yZWxzUEsBAhQACgAAAAAAh07i&#10;QAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAQAAAAAAAAAGRycy9QSwECFAAUAAAACACHTuJApl4olL4A&#10;AADbAAAADwAAAAAAAAABACAAAAAiAAAAZHJzL2Rvd25yZXYueG1sUEsBAhQAFAAAAAgAh07iQDMv&#10;BZ47AAAAOQAAABAAAAAAAAAAAQAgAAAADQEAAGRycy9zaGFwZXhtbC54bWxQSwUGAAAAAAYABgBb&#10;AQAAtwMAAAAA&#10;">
													<v:fill on="f" focussize="0,0" />
													<v:stroke on="f" weight="2.25pt" joinstyle="round"
														endcap="round" />
													<v:imagedata o:title="" />
													<o:lock v:ext="edit" aspectratio="f" />
													<v:textbox>
														<w:txbxContent>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:jc w:val="center" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																	<w:t>姓名</w:t>
																</w:r>
															</w:p>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:jc w:val="center" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																	<w:t>性别</w:t>
																</w:r>
															</w:p>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:jc w:val="center" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																	<w:t>电话</w:t>
																</w:r>
															</w:p>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:jc w:val="center" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:color w:val="FFFFFF" w:themeColor="background1" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																		<w14:textFill>
																			<w14:solidFill>
																				<w14:schemeClr w14:val="bg1" />
																			</w14:solidFill>
																		</w14:textFill>
																	</w:rPr>
																	<w:t>团队</w:t>
																</w:r>
															</w:p>
														</w:txbxContent>
													</v:textbox>
												</v:shape>
												<v:shape id="textbox${c_index}Content" o:spid="_x0000_s1026"
													o:spt="202" type="#_x0000_t202"
													style="position:absolute;left:997;top:5068;height:1361;width:1965;"
													fillcolor="#FFFFFF [3201]" filled="t" stroked="f"
													coordsize="21600,21600"
													o:gfxdata="UEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAEAAAAZHJzL1BLAwQUAAAACACHTuJA0KDLObkAAADb&#10;AAAADwAAAGRycy9kb3ducmV2LnhtbEWPS6vCMBSE94L/IRzBnaa9ikg1uhCEuxJ83K4PzbEpNicl&#10;ic9fbwThLoeZ+YZZrh+2FTfyoXGsIB9nIIgrpxuuFZyO29EcRIjIGlvHpOBJAdarfm+JhXZ33tPt&#10;EGuRIBwKVGBi7AopQ2XIYhi7jjh5Z+ctxiR9LbXHe4LbVv5k2UxabDgtGOxoY6i6HK5WQVnbV/mX&#10;d95o205593oeT65RajjIswWISI/4H/62f7WCyQw+X9IPkKs3UEsDBBQAAAAIAIdO4kAzLwWeOwAA&#10;ADkAAAAQAAAAZHJzL3NoYXBleG1sLnhtbLOxr8jNUShLLSrOzM+zVTLUM1BSSM1Lzk/JzEu3VQoN&#10;cdO1UFIoLknMS0nMyc9LtVWqTC1Wsrfj5QIAUEsDBAoAAAAAAIdO4kAAAAAAAAAAAAAAAAAGAAAA&#10;X3JlbHMvUEsDBBQAAAAIAIdO4kDVXCYozAAAAI8BAAALAAAAX3JlbHMvLnJlbHOlkLFqAzEMhvdA&#10;38Fo7/mSoZQQX7ZC1pBCV2Hr7kzOlrHMNXn7uJRCL2TLoEG/0PcJ7faXMKmZsniOBtZNC4qiZefj&#10;YODz9PH6DkoKRocTRzJwJYF997LaHWnCUpdk9ElUpUQxMJaStlqLHSmgNJwo1knPOWCpbR50QnvG&#10;gfSmbd90/s+AbsFUB2cgH9wG1OmaqvmOHbzNLNyXxnLQ3PfePqJqGTHRV5gqBvNAxYDL8pvW05pa&#10;oB+b10+aHX/HI81L8U+Yaf7z6sUbuxtQSwMEFAAAAAgAh07iQFrjEWb3AAAA4gEAABMAAABbQ29u&#10;dGVudF9UeXBlc10ueG1slZFNT8QgEIbvJv4HMlfTUj0YY0r3YPWoRtcfMIFpS7YFwmDd/ffS/bgY&#10;18QjzLzP+wTq1XYaxUyRrXcKrssKBDntjXW9go/1U3EHghM6g6N3pGBHDKvm8qJe7wKxyGnHCoaU&#10;wr2UrAeakEsfyOVJ5+OEKR9jLwPqDfYkb6rqVmrvErlUpIUBTd1Sh59jEo/bfH0wiTQyiIfD4tKl&#10;AEMYrcaUTeXszI+W4thQ5uR+hwcb+CprgPy1YZmcLzjmXvLTRGtIvGJMzzhlDWkiS+O/XKS5/Buy&#10;WE5c+K6zmso2cptjbzSfrM7RecBAGf1f/PuSO8Hl/oeab1BLAQIUABQAAAAIAIdO4kBa4xFm9wAA&#10;AOIBAAATAAAAAAAAAAEAIAAAAIoCAABbQ29udGVudF9UeXBlc10ueG1sUEsBAhQACgAAAAAAh07i&#10;QAAAAAAAAAAAAAAAAAYAAAAAAAAAAAAQAAAAcQEAAF9yZWxzL1BLAQIUABQAAAAIAIdO4kDVXCYo&#10;zAAAAI8BAAALAAAAAAAAAAEAIAAAAJUBAABfcmVscy8ucmVsc1BLAQIUAAoAAAAAAIdO4kAAAAAA&#10;AAAAAAAAAAAEAAAAAAAAAAAAEAAAAAAAAABkcnMvUEsBAhQAFAAAAAgAh07iQNCgyzm5AAAA2wAA&#10;AA8AAAAAAAAAAQAgAAAAIgAAAGRycy9kb3ducmV2LnhtbFBLAQIUABQAAAAIAIdO4kAzLwWeOwAA&#10;ADkAAAAQAAAAAAAAAAEAIAAAAAgBAABkcnMvc2hhcGV4bWwueG1sUEsFBgAAAAAGAAYAWwEAALID&#10;AAAAAA==&#10;">
													<v:fill on="t" focussize="0,0" />
													<v:stroke on="f" weight="0.5pt" />
													<v:imagedata o:title="" />
													<o:lock v:ext="edit" aspectratio="f" />
													<v:textbox>
														<w:txbxContent>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																	<w:t>${(c.username)!''}</w:t>
																</w:r>
															</w:p>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																	<w:t>${(c.sex)!''}</w:t>
																</w:r>
															</w:p>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																	<w:t>${(c.phone)!''}</w:t>
																</w:r>
															</w:p>
															<w:p>
																<w:pPr>
																	<w:keepNext w:val="0" />
																	<w:keepLines w:val="0" />
																	<w:pageBreakBefore w:val="0" />
																	<w:widowControl w:val="0" />
																	<w:kinsoku />
																	<w:wordWrap />
																	<w:overflowPunct />
																	<w:topLinePunct w:val="0" />
																	<w:autoSpaceDE />
																	<w:autoSpaceDN />
																	<w:bidi w:val="0" />
																	<w:adjustRightInd />
																	<w:snapToGrid />
																	<w:spacing w:line="160" w:lineRule="exact" />
																	<w:textAlignment w:val="auto" />
																	<w:rPr>
																		<w:rFonts w:hint="default" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																</w:pPr>
																<w:r>
																	<w:rPr>
																		<w:rFonts w:hint="eastAsia" w:ascii="苹方 粗体"
																			w:hAnsi="苹方 粗体" w:eastAsia="苹方 粗体" w:cs="苹方 粗体" />
																		<w:sz w:val="10" />
																		<w:szCs w:val="10" />
																		<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																	</w:rPr>
																	<w:t>${(c.levelName)!''}</w:t>
																</w:r>
															</w:p>
														</w:txbxContent>
													</v:textbox>
												</v:shape>
											</v:group>
											<v:shape id="_x0000_s1026" o:spid="_x0000_s1026"
												o:spt="202" type="#_x0000_t202"
												style="position:absolute;left:608965;top:1990090;height:281305;width:704850;"
												filled="f" stroked="f" coordsize="21600,21600"
												o:gfxdata="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&#10;AAAABgAGAFkBAADCBQAAAAA=&#10;">
												<v:fill on="f" focussize="0,0" />
												<v:stroke on="f" weight="0.5pt" />
												<v:imagedata o:title="" />
												<o:lock v:ext="edit" aspectratio="f" />
												<v:textbox>
													<w:txbxContent>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="140" w:lineRule="exact" />
																<w:jc w:val="center" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:rFonts w:hint="default" w:eastAsiaTheme="minorEastAsia" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="7F7F7F" w:themeColor="background1"
																		w:themeShade="80" />
																	<w:w w:val="100" />
																	<w:sz w:val="10" />
																	<w:szCs w:val="10" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="7F7F7F" w:themeColor="background1"
																		w:themeShade="80" />
																	<w:w w:val="100" />
																	<w:sz w:val="10" />
																	<w:szCs w:val="10" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t>二维码</w:t>
															</w:r>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:eastAsiaTheme="minorEastAsia" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="7F7F7F" w:themeColor="background1"
																		w:themeShade="80" />
																	<w:w w:val="100" />
																	<w:sz w:val="10" />
																	<w:szCs w:val="10" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t>仅向领队</w:t>
															</w:r>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="7F7F7F" w:themeColor="background1"
																		w:themeShade="80" />
																	<w:w w:val="100" />
																	<w:sz w:val="10" />
																	<w:szCs w:val="10" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t>或</w:t>
															</w:r>
														</w:p>
														<w:p>
															<w:pPr>
																<w:keepNext w:val="0" />
																<w:keepLines w:val="0" />
																<w:pageBreakBefore w:val="0" />
																<w:widowControl w:val="0" />
																<w:kinsoku />
																<w:wordWrap />
																<w:overflowPunct />
																<w:topLinePunct w:val="0" />
																<w:autoSpaceDE />
																<w:autoSpaceDN />
																<w:bidi w:val="0" />
																<w:adjustRightInd />
																<w:snapToGrid />
																<w:spacing w:line="140" w:lineRule="exact" />
																<w:jc w:val="center" />
																<w:textAlignment w:val="auto" />
																<w:rPr>
																	<w:sz w:val="10" />
																	<w:szCs w:val="10" />
																</w:rPr>
															</w:pPr>
															<w:r>
																<w:rPr>
																	<w:rFonts w:hint="eastAsia" w:eastAsiaTheme="minorEastAsia" />
																	<w:b />
																	<w:bCs />
																	<w:color w:val="7F7F7F" w:themeColor="background1"
																		w:themeShade="80" />
																	<w:w w:val="100" />
																	<w:sz w:val="10" />
																	<w:szCs w:val="10" />
																	<w:lang w:val="en-US" w:eastAsia="zh-CN" />
																</w:rPr>
																<w:t>社区工作者出示</w:t>
															</w:r>
														</w:p>
													</w:txbxContent>
												</v:textbox>
											</v:shape>
											<w10:wrap type="none" />
											<w10:anchorlock />
										</v:group>
									</w:pict>
								</mc:Fallback>
							</mc:AlternateContent>
						</w:r>
						</#list>
						</#if>
						<!--============================================ 图片-结束==================================================== -->






						<w:bookmarkStart w:id="0" w:name="_GoBack" />
						<w:bookmarkEnd w:id="0" />
					</w:p>
					<w:p>
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia" w:eastAsiaTheme="minorEastAsia" />
								<w:sz w:val="21" />
								<w:vertAlign w:val="subscript" />
								<w:lang w:val="en-US" w:eastAsia="zh-CN" />
							</w:rPr>
						</w:pPr>
					</w:p>
					<w:sectPr>
						<w:pgSz w:w="16838" w:h="11906" w:orient="landscape" />
						<w:pgMar w:top="397" w:right="397" w:bottom="397" w:left="397"
							w:header="851" w:footer="992" w:gutter="0" />
						<w:cols w:space="425" w:num="1" />
						<w:docGrid w:type="lines" w:linePitch="312" w:charSpace="0" />
					</w:sectPr>
				</w:body>
			</w:document>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/customXml/_rels/item1.xml.rels"
		pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
		<pkg:xmlData>
			<Relationships
				xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
				<Relationship Id="rId1"
					Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXmlProps"
					Target="itemProps1.xml" />
			</Relationships>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/customXml/item1.xml" pkg:contentType="application/xml">
		<pkg:xmlData>
			<s:customData xmlns="http://www.wps.cn/officeDocument/2013/wpsCustomData"
				xmlns:s="http://www.wps.cn/officeDocument/2013/wpsCustomData">
				<customSectProps>
					<customSectPr />
				</customSectProps>
				<customShpExts>
					<customShpInfo spid="_x0000_s1026" textRotate="1" />
				</customShpExts>
			</s:customData>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/customXml/itemProps1.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.customXmlProperties+xml">
		<pkg:xmlData>
			<ds:datastoreItem ds:itemID="{B1977F7D-205B-4081-913C-38D41E755F92}"
				xmlns:ds="http://schemas.openxmlformats.org/officeDocument/2006/customXml">
				<ds:schemaRefs>
					<ds:schemaRef ds:uri="http://www.wps.cn/officeDocument/2013/wpsCustomData" />
				</ds:schemaRefs>
			</ds:datastoreItem>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/docProps/app.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.extended-properties+xml">
		<pkg:xmlData>
			<Properties
				xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"
				xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
				<Template>Normal.dotm</Template>
				<Pages>1</Pages>
				<Words>0</Words>
				<Characters>0</Characters>
				<Lines>0</Lines>
				<Paragraphs>0</Paragraphs>
				<TotalTime>1</TotalTime>
				<ScaleCrop>false</ScaleCrop>
				<LinksUpToDate>false</LinksUpToDate>
				<CharactersWithSpaces>0</CharactersWithSpaces>
				<Application>WPS
					Office_11.1.0.8584_F1E327BC-269C-435d-A152-05C5408002CA
				</Application>
				<DocSecurity>0</DocSecurity>
			</Properties>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/docProps/core.xml"
		pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml">
		<pkg:xmlData>
			<cp:coreProperties
				xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties"
				xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/"
				xmlns:dcmitype="http://purl.org/dc/dcmitype/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<dcterms:created xsi:type="dcterms:W3CDTF">2014-10-29T12:08:00Z
				</dcterms:created>
				<dc:creator>Administrator</dc:creator>
				<cp:lastModifiedBy>iStHEreaNybOdY</cp:lastModifiedBy>
				<cp:lastPrinted>2019-06-26T01:02:00Z</cp:lastPrinted>
				<dcterms:modified xsi:type="dcterms:W3CDTF">2019-06-26T01:51:45Z
				</dcterms:modified>
			</cp:coreProperties>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/docProps/custom.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.custom-properties+xml">
		<pkg:xmlData>
			<Properties
				xmlns="http://schemas.openxmlformats.org/officeDocument/2006/custom-properties"
				xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
				<property fmtid="{D5CDD505-2E9C-101B-9397-08002B2CF9AE}"
					pid="2" name="KSOProductBuildVer">
					<vt:lpwstr>2052-11.1.0.8584</vt:lpwstr>
				</property>
			</Properties>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/fontTable.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml">
		<pkg:xmlData>
			<w:fonts
				xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
				xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
				xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
				xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
				mc:Ignorable="w14">
				<w:font w:name="Times New Roman">
					<w:panose1 w:val="02020603050405020304" />
					<w:charset w:val="00" />
					<w:family w:val="roman" />
					<w:pitch w:val="variable" />
					<w:sig w:usb0="20007A87" w:usb1="80000000" w:usb2="00000008"
						w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000" />
				</w:font>
				<w:font w:name="宋体">
					<w:panose1 w:val="02010600030101010101" />
					<w:charset w:val="86" />
					<w:family w:val="auto" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="00000003" w:usb1="288F0000" w:usb2="00000006"
						w:usb3="00000000" w:csb0="00040001" w:csb1="00000000" />
				</w:font>
				<w:font w:name="Wingdings">
					<w:panose1 w:val="05000000000000000000" />
					<w:charset w:val="02" />
					<w:family w:val="auto" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="00000000" w:usb1="00000000" w:usb2="00000000"
						w:usb3="00000000" w:csb0="80000000" w:csb1="00000000" />
				</w:font>
				<w:font w:name="Arial">
					<w:panose1 w:val="020B0604020202020204" />
					<w:charset w:val="01" />
					<w:family w:val="swiss" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009"
						w:usb3="00000000" w:csb0="400001FF" w:csb1="FFFF0000" />
				</w:font>
				<w:font w:name="黑体">
					<w:panose1 w:val="02010609060101010101" />
					<w:charset w:val="86" />
					<w:family w:val="auto" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="800002BF" w:usb1="38CF7CFA" w:usb2="00000016"
						w:usb3="00000000" w:csb0="00040001" w:csb1="00000000" />
				</w:font>
				<w:font w:name="Courier New">
					<w:panose1 w:val="02070309020205020404" />
					<w:charset w:val="01" />
					<w:family w:val="modern" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="E0002EFF" w:usb1="C0007843" w:usb2="00000009"
						w:usb3="00000000" w:csb0="400001FF" w:csb1="FFFF0000" />
				</w:font>
				<w:font w:name="Symbol">
					<w:panose1 w:val="05050102010706020507" />
					<w:charset w:val="02" />
					<w:family w:val="roman" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="00000000" w:usb1="00000000" w:usb2="00000000"
						w:usb3="00000000" w:csb0="80000000" w:csb1="00000000" />
				</w:font>
				<w:font w:name="Calibri">
					<w:panose1 w:val="020F0502020204030204" />
					<w:charset w:val="00" />
					<w:family w:val="swiss" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="E0002EFF" w:usb1="C000247B" w:usb2="00000009"
						w:usb3="00000000" w:csb0="200001FF" w:csb1="00000000" />
				</w:font>
				<w:font w:name="Microsoft YaHei UI">
					<w:panose1 w:val="020B0503020204020204" />
					<w:charset w:val="86" />
					<w:family w:val="auto" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="80000287" w:usb1="2ACF3C50" w:usb2="00000016"
						w:usb3="00000000" w:csb0="0004001F" w:csb1="00000000" />
				</w:font>
				<w:font w:name="苹方 粗体">
					<w:panose1 w:val="020B0600000000000000" />
					<w:charset w:val="86" />
					<w:family w:val="auto" />
					<w:pitch w:val="default" />
					<w:sig w:usb0="A00002FF" w:usb1="7ACFFCFB" w:usb2="00000016"
						w:usb3="00000000" w:csb0="00040001" w:csb1="00000000" />
				</w:font>
			</w:fonts>
		</pkg:xmlData>
	</pkg:part>




	<!--============================================ 图片的base64编码字符串所在位置-开始==================================================== -->
	<#if cards?? && (cards?size > 0)>
	<#list cards as c>
	<pkg:part pkg:name="/word/media/image${c_index}.GIF"
		pkg:contentType="image/gif">
		<pkg:binaryData>${(c.qrcodeB64Str)!''}</pkg:binaryData>
	</pkg:part>
	</#list>
	</#if>
	<!--============================================ 图片的base64编码字符串所在位置-结束==================================================== -->





	<pkg:part pkg:name="/word/settings.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml">
		<pkg:xmlData>
			<w:settings
				xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
				xmlns:o="urn:schemas-microsoft-com:office:office"
				xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
				xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
				xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
				xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
				xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
				xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main"
				mc:Ignorable="w14">
				<w:zoom w:percent="120" />
				<w:embedSystemFonts />
				<w:bordersDoNotSurroundHeader w:val="1" />
				<w:bordersDoNotSurroundFooter w:val="1" />
				<w:documentProtection w:enforcement="0" />
				<w:defaultTabStop w:val="420" />
				<w:drawingGridVerticalSpacing w:val="156" />
				<w:displayHorizontalDrawingGridEvery
					w:val="0" />
				<w:displayVerticalDrawingGridEvery
					w:val="2" />
				<w:characterSpacingControl w:val="compressPunctuation" />
				<w:compat>
					<w:spaceForUL />
					<w:balanceSingleByteDoubleByteWidth />
					<w:doNotLeaveBackslashAlone />
					<w:ulTrailSpace />
					<w:doNotExpandShiftReturn />
					<w:adjustLineHeightInTable />
					<w:useFELayout />
					<w:compatSetting w:name="compatibilityMode"
						w:uri="http://schemas.microsoft.com/office/word" w:val="14" />
					<w:compatSetting w:name="overrideTableStyleFontSizeAndJustification"
						w:uri="http://schemas.microsoft.com/office/word" w:val="1" />
					<w:compatSetting w:name="enableOpenTypeFeatures"
						w:uri="http://schemas.microsoft.com/office/word" w:val="1" />
					<w:compatSetting w:name="doNotFlipMirrorIndents"
						w:uri="http://schemas.microsoft.com/office/word" w:val="1" />
				</w:compat>
				<w:rsids>
					<w:rsidRoot w:val="00000000" />
					<w:rsid w:val="0065776C" />
					<w:rsid w:val="015B2A28" />
					<w:rsid w:val="01645508" />
					<w:rsid w:val="02F5358B" />
					<w:rsid w:val="042B45E2" />
					<w:rsid w:val="045A7CF4" />
					<w:rsid w:val="04733516" />
					<w:rsid w:val="04C21211" />
					<w:rsid w:val="05090D86" />
					<w:rsid w:val="065D498C" />
					<w:rsid w:val="06DC0C84" />
					<w:rsid w:val="07887B07" />
					<w:rsid w:val="07F64113" />
					<w:rsid w:val="08587E03" />
					<w:rsid w:val="08D23592" />
					<w:rsid w:val="0B8631BC" />
					<w:rsid w:val="0B8B6CE9" />
					<w:rsid w:val="0BED0EC2" />
					<w:rsid w:val="0CA6612B" />
					<w:rsid w:val="0CB14F60" />
					<w:rsid w:val="0CB379AD" />
					<w:rsid w:val="0D9D67F2" />
					<w:rsid w:val="0DBF4B76" />
					<w:rsid w:val="0DC1660E" />
					<w:rsid w:val="0EFC4D58" />
					<w:rsid w:val="0F1D4376" />
					<w:rsid w:val="114A5B22" />
					<w:rsid w:val="123A3CDF" />
					<w:rsid w:val="124578AC" />
					<w:rsid w:val="12DE5D11" />
					<w:rsid w:val="13374715" />
					<w:rsid w:val="133A1A48" />
					<w:rsid w:val="14D01B86" />
					<w:rsid w:val="14E85E6D" />
					<w:rsid w:val="16B60F9A" />
					<w:rsid w:val="16DF70A9" />
					<w:rsid w:val="180A25FF" />
					<w:rsid w:val="19015FF2" />
					<w:rsid w:val="193A3FEE" />
					<w:rsid w:val="198D24D5" />
					<w:rsid w:val="19DB63A0" />
					<w:rsid w:val="1A1940B7" />
					<w:rsid w:val="1AED2AB2" />
					<w:rsid w:val="1B564ABE" />
					<w:rsid w:val="1BBF7D58" />
					<w:rsid w:val="1BED6FE5" />
					<w:rsid w:val="1C58610F" />
					<w:rsid w:val="1C66483A" />
					<w:rsid w:val="1CA82972" />
					<w:rsid w:val="1CF94EB4" />
					<w:rsid w:val="1E1B161F" />
					<w:rsid w:val="1E9B782E" />
					<w:rsid w:val="1FA216A5" />
					<w:rsid w:val="20AC0D4C" />
					<w:rsid w:val="235D13E5" />
					<w:rsid w:val="2365179D" />
					<w:rsid w:val="23B41428" />
					<w:rsid w:val="24380A4D" />
					<w:rsid w:val="243A24BB" />
					<w:rsid w:val="24C322DA" />
					<w:rsid w:val="24F95419" />
					<w:rsid w:val="26A05223" />
					<w:rsid w:val="271332F4" />
					<w:rsid w:val="276F6AE5" />
					<w:rsid w:val="28F33C2F" />
					<w:rsid w:val="29320289" />
					<w:rsid w:val="2942088B" />
					<w:rsid w:val="29631414" />
					<w:rsid w:val="2A8C76B4" />
					<w:rsid w:val="2AAC1E7A" />
					<w:rsid w:val="2AC674C5" />
					<w:rsid w:val="2E9631D6" />
					<w:rsid w:val="2F0C638B" />
					<w:rsid w:val="305572AB" />
					<w:rsid w:val="30C60786" />
					<w:rsid w:val="32117739" />
					<w:rsid w:val="321B60DB" />
					<w:rsid w:val="32B35187" />
					<w:rsid w:val="332C3C28" />
					<w:rsid w:val="34104B03" />
					<w:rsid w:val="35345130" />
					<w:rsid w:val="355D4802" />
					<w:rsid w:val="35C868FE" />
					<w:rsid w:val="37215E10" />
					<w:rsid w:val="37313C04" />
					<w:rsid w:val="375A59AB" />
					<w:rsid w:val="378E58A0" />
					<w:rsid w:val="37C47EC2" />
					<w:rsid w:val="37C95902" />
					<w:rsid w:val="38054642" />
					<w:rsid w:val="38937A10" />
					<w:rsid w:val="38CD3151" />
					<w:rsid w:val="39BE4D5F" />
					<w:rsid w:val="3AA07969" />
					<w:rsid w:val="3AE83CE6" />
					<w:rsid w:val="3C5F0523" />
					<w:rsid w:val="3C850B84" />
					<w:rsid w:val="3ED859F2" />
					<w:rsid w:val="3F2D456E" />
					<w:rsid w:val="3F823B65" />
					<w:rsid w:val="3F9002B2" />
					<w:rsid w:val="3F9815F4" />
					<w:rsid w:val="41A935F2" />
					<w:rsid w:val="430766A9" />
					<w:rsid w:val="431202F9" />
					<w:rsid w:val="43DA3727" />
					<w:rsid w:val="45496E6F" />
					<w:rsid w:val="456F368A" />
					<w:rsid w:val="470E6439" />
					<w:rsid w:val="476742D5" />
					<w:rsid w:val="47945800" />
					<w:rsid w:val="47A923C0" />
					<w:rsid w:val="48E23906" />
					<w:rsid w:val="49115293" />
					<w:rsid w:val="4C275EA8" />
					<w:rsid w:val="4C5518BB" />
					<w:rsid w:val="4CC36B8C" />
					<w:rsid w:val="4D1E65EB" />
					<w:rsid w:val="4D702A1C" />
					<w:rsid w:val="4F4B5F05" />
					<w:rsid w:val="4FC33DA7" />
					<w:rsid w:val="50543AD9" />
					<w:rsid w:val="513D3EFD" />
					<w:rsid w:val="517A393E" />
					<w:rsid w:val="51D12327" />
					<w:rsid w:val="51FC2EDD" />
					<w:rsid w:val="52BF1A5C" />
					<w:rsid w:val="52C34802" />
					<w:rsid w:val="564766BB" />
					<w:rsid w:val="59984897" />
					<w:rsid w:val="5A157C56" />
					<w:rsid w:val="5A724702" />
					<w:rsid w:val="5A9509E8" />
					<w:rsid w:val="5AA3017C" />
					<w:rsid w:val="5B062760" />
					<w:rsid w:val="5B6E1389" />
					<w:rsid w:val="5D9F4B14" />
					<w:rsid w:val="5FCD0D4D" />
					<w:rsid w:val="603D52CA" />
					<w:rsid w:val="60EF4209" />
					<w:rsid w:val="60FD3281" />
					<w:rsid w:val="62586FE4" />
					<w:rsid w:val="63896EBB" />
					<w:rsid w:val="63B07A1A" />
					<w:rsid w:val="63B22887" />
					<w:rsid w:val="66CE4C1C" />
					<w:rsid w:val="66E60B49" />
					<w:rsid w:val="67443FF2" />
					<w:rsid w:val="67595956" />
					<w:rsid w:val="68AB0607" />
					<w:rsid w:val="68CD688F" />
					<w:rsid w:val="697B5D8D" />
					<w:rsid w:val="6BB17888" />
					<w:rsid w:val="6BBA24B7" />
					<w:rsid w:val="6E0B4238" />
					<w:rsid w:val="6E9D7948" />
					<w:rsid w:val="6F9679AF" />
					<w:rsid w:val="6FB476AF" />
					<w:rsid w:val="70777746" />
					<w:rsid w:val="715156ED" />
					<w:rsid w:val="72553287" />
					<w:rsid w:val="730B207A" />
					<w:rsid w:val="746B3A21" />
					<w:rsid w:val="750530C3" />
					<w:rsid w:val="752C0F13" />
					<w:rsid w:val="75351534" />
					<w:rsid w:val="75F7493B" />
					<w:rsid w:val="78300A0C" />
					<w:rsid w:val="78CD28C0" />
					<w:rsid w:val="79062027" />
					<w:rsid w:val="799F2E53" />
					<w:rsid w:val="7ABB76DB" />
					<w:rsid w:val="7AC763DE" />
					<w:rsid w:val="7B8E7751" />
					<w:rsid w:val="7BBA46AB" />
					<w:rsid w:val="7BEE17A2" />
					<w:rsid w:val="7E0A3ED1" />
					<w:rsid w:val="7E1B714E" />
					<w:rsid w:val="7F40163D" />
					<w:rsid w:val="7F5040DB" />
				</w:rsids>
				<m:mathPr>
					<m:mathFont m:val="Cambria Math" />
					<m:brkBin m:val="before" />
					<m:brkBinSub m:val="--" />
					<m:smallFrac m:val="0" />
					<m:dispDef />
					<m:lMargin m:val="0" />
					<m:rMargin m:val="0" />
					<m:defJc m:val="centerGroup" />
					<m:wrapIndent m:val="1440" />
					<m:intLim m:val="subSup" />
					<m:naryLim m:val="undOvr" />
				</m:mathPr>
				<w:themeFontLang w:val="en-US" w:eastAsia="zh-CN" />
				<w:clrSchemeMapping w:bg1="light1" w:t1="dark1"
					w:bg2="light2" w:t2="dark2" w:accent1="accent1" w:accent2="accent2"
					w:accent3="accent3" w:accent4="accent4" w:accent5="accent5"
					w:accent6="accent6" w:hyperlink="hyperlink" w:followedHyperlink="followedHyperlink" />
				<w:doNotIncludeSubdocsInStats />
				<w:shapeDefaults>
					<o:shapedefaults fillcolor="#FFFFFF" fill="t"
						stroke="t">
						<v:fill on="t" focussize="0,0" />
						<v:stroke color="#000000" />
					</o:shapedefaults>
					<o:shapelayout v:ext="edit">
						<o:idmap v:ext="edit" data="1" />
					</o:shapelayout>
				</w:shapeDefaults>
			</w:settings>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/styles.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml">
		<pkg:xmlData>
			<w:styles
				xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
				xmlns:o="urn:schemas-microsoft-com:office:office"
				xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
				xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
				xmlns:v="urn:schemas-microsoft-com:vml"
				xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
				xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
				xmlns:w10="urn:schemas-microsoft-com:office:word"
				xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main"
				mc:Ignorable="w14">
				<w:docDefaults>
					<w:rPrDefault>
						<w:rPr>
							<w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi"
								w:eastAsiaTheme="minorEastAsia" w:cstheme="minorBidi" />
						</w:rPr>
					</w:rPrDefault>
				</w:docDefaults>
				<w:latentStyles w:count="260" w:defQFormat="0"
					w:defUnhideWhenUsed="1" w:defSemiHidden="1" w:defUIPriority="99"
					w:defLockedState="0">
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:semiHidden="0" w:name="Normal" />
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:semiHidden="0" w:name="heading 1" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 2" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 3" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 4" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 5" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 6" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 7" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 8" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="heading 9" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 7" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 8" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index 9" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 7" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 8" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toc 9" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Normal Indent" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="footnote text" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="annotation text" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="header" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="footer" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="index heading" />
					<w:lsdException w:qFormat="1" w:uiPriority="0"
						w:name="caption" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="table of figures" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="envelope address" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="envelope return" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="footnote reference" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="annotation reference" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="line number" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="page number" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="endnote reference" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="endnote text" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="table of authorities" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="macro" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="toa heading" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Bullet" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Number" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Bullet 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Bullet 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Bullet 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Bullet 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Number 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Number 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Number 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Number 5" />
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:semiHidden="0" w:name="Title" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Closing" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Signature" />
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:name="Default Paragraph Font" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text Indent" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Continue" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Continue 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Continue 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Continue 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="List Continue 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Message Header" />
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:semiHidden="0" w:name="Subtitle" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Salutation" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Date" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text First Indent" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text First Indent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Note Heading" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text Indent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Body Text Indent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Block Text" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Hyperlink" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="FollowedHyperlink" />
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:semiHidden="0" w:name="Strong" />
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:semiHidden="0" w:name="Emphasis" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Document Map" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Plain Text" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="E-mail Signature" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Normal (Web)" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Acronym" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Address" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Cite" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Code" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Definition" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Keyboard" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Preformatted" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Sample" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Typewriter" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="HTML Variable" />
					<w:lsdException w:qFormat="1" w:unhideWhenUsed="0"
						w:uiPriority="0" w:name="Normal Table" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="annotation subject" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Simple 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Simple 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Simple 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Classic 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Classic 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Classic 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Classic 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Colorful 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Colorful 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Colorful 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Columns 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Columns 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Columns 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Columns 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Columns 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 7" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid 8" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 7" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table List 8" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table 3D effects 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table 3D effects 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table 3D effects 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Contemporary" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Elegant" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Professional" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Subtle 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Subtle 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Web 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Web 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Web 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Balloon Text" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Grid" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="0"
						w:semiHidden="0" w:name="Table Theme" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="60"
						w:semiHidden="0" w:name="Light Shading" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="61"
						w:semiHidden="0" w:name="Light List" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="62"
						w:semiHidden="0" w:name="Light Grid" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="63"
						w:semiHidden="0" w:name="Medium Shading 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="64"
						w:semiHidden="0" w:name="Medium Shading 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="65"
						w:semiHidden="0" w:name="Medium List 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="66"
						w:semiHidden="0" w:name="Medium List 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="67"
						w:semiHidden="0" w:name="Medium Grid 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="68"
						w:semiHidden="0" w:name="Medium Grid 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="69"
						w:semiHidden="0" w:name="Medium Grid 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="70"
						w:semiHidden="0" w:name="Dark List" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="71"
						w:semiHidden="0" w:name="Colorful Shading" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="72"
						w:semiHidden="0" w:name="Colorful List" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="73"
						w:semiHidden="0" w:name="Colorful Grid" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="60"
						w:semiHidden="0" w:name="Light Shading Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="61"
						w:semiHidden="0" w:name="Light List Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="62"
						w:semiHidden="0" w:name="Light Grid Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="63"
						w:semiHidden="0" w:name="Medium Shading 1 Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="64"
						w:semiHidden="0" w:name="Medium Shading 2 Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="65"
						w:semiHidden="0" w:name="Medium List 1 Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="66"
						w:semiHidden="0" w:name="Medium List 2 Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="67"
						w:semiHidden="0" w:name="Medium Grid 1 Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="68"
						w:semiHidden="0" w:name="Medium Grid 2 Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="69"
						w:semiHidden="0" w:name="Medium Grid 3 Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="70"
						w:semiHidden="0" w:name="Dark List Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="71"
						w:semiHidden="0" w:name="Colorful Shading Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="72"
						w:semiHidden="0" w:name="Colorful List Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="73"
						w:semiHidden="0" w:name="Colorful Grid Accent 1" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="60"
						w:semiHidden="0" w:name="Light Shading Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="61"
						w:semiHidden="0" w:name="Light List Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="62"
						w:semiHidden="0" w:name="Light Grid Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="63"
						w:semiHidden="0" w:name="Medium Shading 1 Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="64"
						w:semiHidden="0" w:name="Medium Shading 2 Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="65"
						w:semiHidden="0" w:name="Medium List 1 Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="66"
						w:semiHidden="0" w:name="Medium List 2 Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="67"
						w:semiHidden="0" w:name="Medium Grid 1 Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="68"
						w:semiHidden="0" w:name="Medium Grid 2 Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="69"
						w:semiHidden="0" w:name="Medium Grid 3 Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="70"
						w:semiHidden="0" w:name="Dark List Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="71"
						w:semiHidden="0" w:name="Colorful Shading Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="72"
						w:semiHidden="0" w:name="Colorful List Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="73"
						w:semiHidden="0" w:name="Colorful Grid Accent 2" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="60"
						w:semiHidden="0" w:name="Light Shading Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="61"
						w:semiHidden="0" w:name="Light List Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="62"
						w:semiHidden="0" w:name="Light Grid Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="63"
						w:semiHidden="0" w:name="Medium Shading 1 Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="64"
						w:semiHidden="0" w:name="Medium Shading 2 Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="65"
						w:semiHidden="0" w:name="Medium List 1 Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="66"
						w:semiHidden="0" w:name="Medium List 2 Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="67"
						w:semiHidden="0" w:name="Medium Grid 1 Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="68"
						w:semiHidden="0" w:name="Medium Grid 2 Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="69"
						w:semiHidden="0" w:name="Medium Grid 3 Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="70"
						w:semiHidden="0" w:name="Dark List Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="71"
						w:semiHidden="0" w:name="Colorful Shading Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="72"
						w:semiHidden="0" w:name="Colorful List Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="73"
						w:semiHidden="0" w:name="Colorful Grid Accent 3" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="60"
						w:semiHidden="0" w:name="Light Shading Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="61"
						w:semiHidden="0" w:name="Light List Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="62"
						w:semiHidden="0" w:name="Light Grid Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="63"
						w:semiHidden="0" w:name="Medium Shading 1 Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="64"
						w:semiHidden="0" w:name="Medium Shading 2 Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="65"
						w:semiHidden="0" w:name="Medium List 1 Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="66"
						w:semiHidden="0" w:name="Medium List 2 Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="67"
						w:semiHidden="0" w:name="Medium Grid 1 Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="68"
						w:semiHidden="0" w:name="Medium Grid 2 Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="69"
						w:semiHidden="0" w:name="Medium Grid 3 Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="70"
						w:semiHidden="0" w:name="Dark List Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="71"
						w:semiHidden="0" w:name="Colorful Shading Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="72"
						w:semiHidden="0" w:name="Colorful List Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="73"
						w:semiHidden="0" w:name="Colorful Grid Accent 4" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="60"
						w:semiHidden="0" w:name="Light Shading Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="61"
						w:semiHidden="0" w:name="Light List Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="62"
						w:semiHidden="0" w:name="Light Grid Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="63"
						w:semiHidden="0" w:name="Medium Shading 1 Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="64"
						w:semiHidden="0" w:name="Medium Shading 2 Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="65"
						w:semiHidden="0" w:name="Medium List 1 Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="66"
						w:semiHidden="0" w:name="Medium List 2 Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="67"
						w:semiHidden="0" w:name="Medium Grid 1 Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="68"
						w:semiHidden="0" w:name="Medium Grid 2 Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="69"
						w:semiHidden="0" w:name="Medium Grid 3 Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="70"
						w:semiHidden="0" w:name="Dark List Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="71"
						w:semiHidden="0" w:name="Colorful Shading Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="72"
						w:semiHidden="0" w:name="Colorful List Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="73"
						w:semiHidden="0" w:name="Colorful Grid Accent 5" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="60"
						w:semiHidden="0" w:name="Light Shading Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="61"
						w:semiHidden="0" w:name="Light List Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="62"
						w:semiHidden="0" w:name="Light Grid Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="63"
						w:semiHidden="0" w:name="Medium Shading 1 Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="64"
						w:semiHidden="0" w:name="Medium Shading 2 Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="65"
						w:semiHidden="0" w:name="Medium List 1 Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="66"
						w:semiHidden="0" w:name="Medium List 2 Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="67"
						w:semiHidden="0" w:name="Medium Grid 1 Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="68"
						w:semiHidden="0" w:name="Medium Grid 2 Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="69"
						w:semiHidden="0" w:name="Medium Grid 3 Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="70"
						w:semiHidden="0" w:name="Dark List Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="71"
						w:semiHidden="0" w:name="Colorful Shading Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="72"
						w:semiHidden="0" w:name="Colorful List Accent 6" />
					<w:lsdException w:unhideWhenUsed="0" w:uiPriority="73"
						w:semiHidden="0" w:name="Colorful Grid Accent 6" />
				</w:latentStyles>
				<w:style w:type="paragraph" w:default="1" w:styleId="1">
					<w:name w:val="Normal" />
					<w:qFormat />
					<w:uiPriority w:val="0" />
					<w:pPr>
						<w:widowControl w:val="0" />
						<w:jc w:val="both" />
					</w:pPr>
					<w:rPr>
						<w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi"
							w:eastAsiaTheme="minorEastAsia" w:cstheme="minorBidi" />
						<w:kern w:val="2" />
						<w:sz w:val="21" />
						<w:szCs w:val="24" />
						<w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA" />
					</w:rPr>
				</w:style>
				<w:style w:type="character" w:default="1" w:styleId="3">
					<w:name w:val="Default Paragraph Font" />
					<w:semiHidden />
					<w:qFormat />
					<w:uiPriority w:val="0" />
				</w:style>
				<w:style w:type="table" w:default="1" w:styleId="2">
					<w:name w:val="Normal Table" />
					<w:semiHidden />
					<w:qFormat />
					<w:uiPriority w:val="0" />
					<w:tblPr>
						<w:tblLayout w:type="fixed" />
						<w:tblCellMar>
							<w:top w:w="0" w:type="dxa" />
							<w:left w:w="108" w:type="dxa" />
							<w:bottom w:w="0" w:type="dxa" />
							<w:right w:w="108" w:type="dxa" />
						</w:tblCellMar>
					</w:tblPr>
				</w:style>
			</w:styles>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/theme/theme1.xml"
		pkg:contentType="application/vnd.openxmlformats-officedocument.theme+xml">
		<pkg:xmlData>
			<a:theme xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"
				name="Office 主题">
				<a:themeElements>
					<a:clrScheme name="Concourse">
						<a:dk1>
							<a:sysClr val="windowText" lastClr="000000" />
						</a:dk1>
						<a:lt1>
							<a:sysClr val="window" lastClr="FFFFFF" />
						</a:lt1>
						<a:dk2>
							<a:srgbClr val="464646" />
						</a:dk2>
						<a:lt2>
							<a:srgbClr val="DEF5FA" />
						</a:lt2>
						<a:accent1>
							<a:srgbClr val="2DA2BF" />
						</a:accent1>
						<a:accent2>
							<a:srgbClr val="DA1F28" />
						</a:accent2>
						<a:accent3>
							<a:srgbClr val="EB641B" />
						</a:accent3>
						<a:accent4>
							<a:srgbClr val="39639D" />
						</a:accent4>
						<a:accent5>
							<a:srgbClr val="474B78" />
						</a:accent5>
						<a:accent6>
							<a:srgbClr val="7D3C4A" />
						</a:accent6>
						<a:hlink>
							<a:srgbClr val="FF8119" />
						</a:hlink>
						<a:folHlink>
							<a:srgbClr val="44B9E8" />
						</a:folHlink>
					</a:clrScheme>
					<a:fontScheme name="Office">
						<a:majorFont>
							<a:latin typeface="Calibri Light" />
							<a:ea typeface="" />
							<a:cs typeface="" />
							<a:font script="Jpan" typeface="ＭＳ ゴシック" />
							<a:font script="Hang" typeface="맑은 고딕" />
							<a:font script="Hans" typeface="宋体" />
							<a:font script="Hant" typeface="新細明體" />
							<a:font script="Arab" typeface="Times New Roman" />
							<a:font script="Hebr" typeface="Times New Roman" />
							<a:font script="Thai" typeface="Angsana New" />
							<a:font script="Ethi" typeface="Nyala" />
							<a:font script="Beng" typeface="Vrinda" />
							<a:font script="Gujr" typeface="Shruti" />
							<a:font script="Khmr" typeface="MoolBoran" />
							<a:font script="Knda" typeface="Tunga" />
							<a:font script="Guru" typeface="Raavi" />
							<a:font script="Cans" typeface="Euphemia" />
							<a:font script="Cher" typeface="Plantagenet Cherokee" />
							<a:font script="Yiii" typeface="Microsoft Yi Baiti" />
							<a:font script="Tibt" typeface="Microsoft Himalaya" />
							<a:font script="Thaa" typeface="MV Boli" />
							<a:font script="Deva" typeface="Mangal" />
							<a:font script="Telu" typeface="Gautami" />
							<a:font script="Taml" typeface="Latha" />
							<a:font script="Syrc" typeface="Estrangelo Edessa" />
							<a:font script="Orya" typeface="Kalinga" />
							<a:font script="Mlym" typeface="Kartika" />
							<a:font script="Laoo" typeface="DokChampa" />
							<a:font script="Sinh" typeface="Iskoola Pota" />
							<a:font script="Mong" typeface="Mongolian Baiti" />
							<a:font script="Viet" typeface="Times New Roman" />
							<a:font script="Uigh" typeface="Microsoft Uighur" />
							<a:font script="Geor" typeface="Sylfaen" />
						</a:majorFont>
						<a:minorFont>
							<a:latin typeface="Calibri" />
							<a:ea typeface="" />
							<a:cs typeface="" />
							<a:font script="Jpan" typeface="ＭＳ 明朝" />
							<a:font script="Hang" typeface="맑은 고딕" />
							<a:font script="Hans" typeface="宋体" />
							<a:font script="Hant" typeface="新細明體" />
							<a:font script="Arab" typeface="Arial" />
							<a:font script="Hebr" typeface="Arial" />
							<a:font script="Thai" typeface="Cordia New" />
							<a:font script="Ethi" typeface="Nyala" />
							<a:font script="Beng" typeface="Vrinda" />
							<a:font script="Gujr" typeface="Shruti" />
							<a:font script="Khmr" typeface="DaunPenh" />
							<a:font script="Knda" typeface="Tunga" />
							<a:font script="Guru" typeface="Raavi" />
							<a:font script="Cans" typeface="Euphemia" />
							<a:font script="Cher" typeface="Plantagenet Cherokee" />
							<a:font script="Yiii" typeface="Microsoft Yi Baiti" />
							<a:font script="Tibt" typeface="Microsoft Himalaya" />
							<a:font script="Thaa" typeface="MV Boli" />
							<a:font script="Deva" typeface="Mangal" />
							<a:font script="Telu" typeface="Gautami" />
							<a:font script="Taml" typeface="Latha" />
							<a:font script="Syrc" typeface="Estrangelo Edessa" />
							<a:font script="Orya" typeface="Kalinga" />
							<a:font script="Mlym" typeface="Kartika" />
							<a:font script="Laoo" typeface="DokChampa" />
							<a:font script="Sinh" typeface="Iskoola Pota" />
							<a:font script="Mong" typeface="Mongolian Baiti" />
							<a:font script="Viet" typeface="Arial" />
							<a:font script="Uigh" typeface="Microsoft Uighur" />
							<a:font script="Geor" typeface="Sylfaen" />
						</a:minorFont>
					</a:fontScheme>
					<a:fmtScheme name="Office">
						<a:fillStyleLst>
							<a:solidFill>
								<a:schemeClr val="phClr" />
							</a:solidFill>
							<a:gradFill rotWithShape="1">
								<a:gsLst>
									<a:gs pos="0">
										<a:schemeClr val="phClr">
											<a:lumMod val="110000" />
											<a:satMod val="105000" />
											<a:tint val="67000" />
										</a:schemeClr>
									</a:gs>
									<a:gs pos="50000">
										<a:schemeClr val="phClr">
											<a:lumMod val="105000" />
											<a:satMod val="103000" />
											<a:tint val="73000" />
										</a:schemeClr>
									</a:gs>
									<a:gs pos="100000">
										<a:schemeClr val="phClr">
											<a:lumMod val="105000" />
											<a:satMod val="109000" />
											<a:tint val="81000" />
										</a:schemeClr>
									</a:gs>
								</a:gsLst>
								<a:lin ang="5400000" scaled="0" />
							</a:gradFill>
							<a:gradFill rotWithShape="1">
								<a:gsLst>
									<a:gs pos="0">
										<a:schemeClr val="phClr">
											<a:satMod val="103000" />
											<a:lumMod val="102000" />
											<a:tint val="94000" />
										</a:schemeClr>
									</a:gs>
									<a:gs pos="50000">
										<a:schemeClr val="phClr">
											<a:satMod val="110000" />
											<a:lumMod val="100000" />
											<a:shade val="100000" />
										</a:schemeClr>
									</a:gs>
									<a:gs pos="100000">
										<a:schemeClr val="phClr">
											<a:lumMod val="99000" />
											<a:satMod val="120000" />
											<a:shade val="78000" />
										</a:schemeClr>
									</a:gs>
								</a:gsLst>
								<a:lin ang="5400000" scaled="0" />
							</a:gradFill>
						</a:fillStyleLst>
						<a:lnStyleLst>
							<a:ln w="6350" cap="flat" cmpd="sng" algn="ctr">
								<a:solidFill>
									<a:schemeClr val="phClr" />
								</a:solidFill>
								<a:prstDash val="solid" />
								<a:miter lim="800000" />
							</a:ln>
							<a:ln w="12700" cap="flat" cmpd="sng" algn="ctr">
								<a:solidFill>
									<a:schemeClr val="phClr" />
								</a:solidFill>
								<a:prstDash val="solid" />
								<a:miter lim="800000" />
							</a:ln>
							<a:ln w="19050" cap="flat" cmpd="sng" algn="ctr">
								<a:solidFill>
									<a:schemeClr val="phClr" />
								</a:solidFill>
								<a:prstDash val="solid" />
								<a:miter lim="800000" />
							</a:ln>
						</a:lnStyleLst>
						<a:effectStyleLst>
							<a:effectStyle>
								<a:effectLst />
							</a:effectStyle>
							<a:effectStyle>
								<a:effectLst />
							</a:effectStyle>
							<a:effectStyle>
								<a:effectLst>
									<a:outerShdw blurRad="57150" dist="19050" dir="5400000"
										algn="ctr" rotWithShape="0">
										<a:srgbClr val="000000">
											<a:alpha val="63000" />
										</a:srgbClr>
									</a:outerShdw>
								</a:effectLst>
							</a:effectStyle>
						</a:effectStyleLst>
						<a:bgFillStyleLst>
							<a:solidFill>
								<a:schemeClr val="phClr" />
							</a:solidFill>
							<a:solidFill>
								<a:schemeClr val="phClr">
									<a:tint val="95000" />
									<a:satMod val="170000" />
								</a:schemeClr>
							</a:solidFill>
							<a:gradFill rotWithShape="1">
								<a:gsLst>
									<a:gs pos="0">
										<a:schemeClr val="phClr">
											<a:tint val="93000" />
											<a:satMod val="150000" />
											<a:shade val="98000" />
											<a:lumMod val="102000" />
										</a:schemeClr>
									</a:gs>
									<a:gs pos="50000">
										<a:schemeClr val="phClr">
											<a:tint val="98000" />
											<a:satMod val="130000" />
											<a:shade val="90000" />
											<a:lumMod val="103000" />
										</a:schemeClr>
									</a:gs>
									<a:gs pos="100000">
										<a:schemeClr val="phClr">
											<a:shade val="63000" />
											<a:satMod val="120000" />
										</a:schemeClr>
									</a:gs>
								</a:gsLst>
								<a:lin ang="5400000" scaled="0" />
							</a:gradFill>
						</a:bgFillStyleLst>
					</a:fmtScheme>
				</a:themeElements>
				<a:objectDefaults />
			</a:theme>
		</pkg:xmlData>
	</pkg:part>
</pkg:package>