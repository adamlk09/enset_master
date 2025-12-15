<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
<html>
<head>
    <title>Relevé Bancaire</title>
    <style>
        table { border-collapse: collapse; width: 80%; }
        th, td { border: 1px solid black; padding: 8px; }
        th { background-color: #dddddd; }
    </style>
</head>

<body>
    <h2>Relevé Bancaire</h2>

    <p><strong>RIB :</strong> <xsl:value-of select="releve/@RIB"/></p>
    <p><strong>Date du relevé :</strong> <xsl:value-of select="releve/dateReleve"/></p>
    <p><strong>Solde :</strong> <xsl:value-of select="releve/solde"/> DH</p>

    <h3>Liste des opérations</h3>

    <table>
        <tr>
            <th>Type</th>
            <th>Date</th>
            <th>Montant</th>
            <th>Description</th>
        </tr>

        <xsl:for-each select="releve/operations/operation">
            <tr>
                <td><xsl:value-of select="@type"/></td>
                <td><xsl:value-of select="@date"/></td>
                <td><xsl:value-of select="@montant"/></td>
                <td><xsl:value-of select="@description"/></td>
            </tr>
        </xsl:for-each>
    </table>

    <h3>Total Crédit :
        <xsl:value-of select="sum(releve/operations/operation[@type='CREDIT']/@montant)"/> DH
    </h3>

    <h3>Total Débit :
        <xsl:value-of select="sum(releve/operations/operation[@type='DEBIT']/@montant)"/> DH
    </h3>

</body>
</html>
</xsl:template>

</xsl:stylesheet>
