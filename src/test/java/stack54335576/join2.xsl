<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	

	<!-- The java app will dynamically source in files like: <xsl:param name="bookFile" 
		select="document('src/test/java/stack54335576/books.xml')"/> <xsl:param name="articleFile" 
		select="document('src/test/java/stack54335576/articles.xml')"/> -->
	<xsl:param name="bookFile"/>
	<xsl:param name="articleFile"/>

	<xsl:output method="xml" encoding="UTF-8" indent="yes" />

	<xsl:template match="/">
		<titleStatusJoin>
			<xsl:for-each select="//book">
				<xsl:variable name="myId" select="id" />
				<book>
					<status>
						<xsl:value-of select="status" />
					</status>
					<title>
						<xsl:for-each select="document($bookFile)//book">
							<xsl:variable name="bookId" select="@id" />
							<xsl:choose>
								<xsl:when test="$myId = $bookId">
									<xsl:value-of select="title" />
								</xsl:when>
							</xsl:choose>
						</xsl:for-each>
					</title>
				</book>
			</xsl:for-each>
			<xsl:for-each select="//article">
				<xsl:variable name="myId" select="id" />
				<article>
					<status>
						<xsl:value-of select="status" />
					</status>
					<title>
						<xsl:for-each select="document($articleFile)//article">
							<xsl:variable name="bookId" select="@id" />
							<xsl:choose>
								<xsl:when test="$myId = $bookId">
									<xsl:value-of select="title" />
								</xsl:when>
							</xsl:choose>
						</xsl:for-each>
					</title>
				</article>
			</xsl:for-each>

		</titleStatusJoin>
	</xsl:template>
</xsl:stylesheet>