<?xml version='1.0'?> 
<!DOCTYPE stylesheet [
    <!ENTITY dbdir "docbook-xsl-ns-1.78.1">
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  
                version="1.0"
                xmlns:d="http://docbook.org/ns/docbook"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

  <xsl:import href= "docbook-xsl-ns\fo\docbook.xsl"/> 

  <xsl:output method="xml" indent="yes"/>
  
<!-- setting paper size -->
  <xsl:param name="paper.type" select="'A4'"/>

<!-- titles with the same typeface as the text (default is 'sans-serif') -->
  <xsl:param name="title.font.family" select="'serif'"/>
  
<!-- ensuring a page break before first-level sections -->
  <xsl:attribute-set name="section.level1.properties">
  	<xsl:attribute name="break-before">page</xsl:attribute>
  </xsl:attribute-set>
  
<!-- special rule for quotations -->
  <xsl:template match="d:quote">
  	<fo:block font-family="sans-serif">
  		<xsl:apply-imports />
  	</fo:block>
  </xsl:template>

</xsl:stylesheet>