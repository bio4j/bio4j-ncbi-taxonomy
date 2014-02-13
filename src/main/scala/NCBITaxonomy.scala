package ohnosequences.bio4j.bundles

import shapeless._
import shapeless.ops.hlist._
import ohnosequences.typesets._
import ohnosequences.statika._
import ohnosequences.statika.aws._
import ohnosequences.statika.ami._
import ohnosequences.bio4j.statika._
import ohnosequences.awstools.s3._
import ohnosequences.awstools.regions._
import com.ohnosequences.bio4j.titan.programs._
import java.io._


case object NCBITaxonomyRawData 
  extends RawDataBundle("ftp://ftp.ncbi.nih.gov/pub/taxonomy/taxdump.tar.gz")

case object NCBITaxonomyAPI extends APIBundle(){
  // def getTaxonByName(String taxonName): Taxon
  // def getNCBITaxonByTaxId(String taxId): NCBITaxon
}

case class NCBITaxonomyProgram(
  nodes         : File,    // 1. Nodes DMP file
  names         : File,    // 2. Names DMP file
  merged        : File,    // 3. Merged DMP file
  db            : File,    // 4. Bio4j DB folder
  assocUnitprot : Boolean  // 5. Associate UniprotKB taxonomy (true/false)
) extends ImporterProgram(new ImportNCBITaxonomyTitan(), Seq(
  nodes.getAbsolutePath, 
  names.getAbsolutePath, 
  merged.getAbsolutePath,
  db.getAbsolutePath,
  assocUnitprot.toString
))

case object NCBITaxonomyImportedData extends ImportedDataBundle(
    rawData = NCBITaxonomyRawData :~: ∅,
    initDB = InitialBio4j
  ) {
  override def install[D <: AnyDistribution](d: D): InstallResults = {
    NCBITaxonomyProgram(
      nodes  = NCBITaxonomyRawData.inDataFolder("nodes.dmp"),
      names  = NCBITaxonomyRawData.inDataFolder("names.dmp"),
      merged = NCBITaxonomyRawData.inDataFolder("merged.dmp"),
      db     = dbLocation,
      assocUnitprot = true
    ).execute ->-
    success(s"Data ${name} is imported to ${dbLocation}")
  }
}

case object NCBITaxonomyModule extends ModuleBundle(NCBITaxonomyAPI, NCBITaxonomyImportedData)

case object NCBITaxonomyMetadata extends generated.metadata.NcbiTaxonomyModule()

case object NCBITaxonomyRelease extends ReleaseBundle(
  ObjectAddress("bio4j.releases", 
                "ncbi_taxonomy/v" + NCBITaxonomyMetadata.version.stripSuffix("-SNAPSHOT")), 
  NCBITaxonomyModule
)

case object NCBITaxonomyDistribution extends DistributionBundle(
  NCBITaxonomyRelease,
  destPrefix = new File("/media/ephemeral0/")
)

