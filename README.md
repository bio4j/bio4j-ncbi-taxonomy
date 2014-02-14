## NCBI Taxonomy Bio4j module

This is a Bio4j module representing NCBI Taxonomy. Find more information about modules in [bio4j/modules](https://github.com/bio4j/modules).

> The [NCBI Taxonomy database](http://www.ncbi.nlm.nih.gov/taxonomy) is the standard nomenclature and classification repository for the International Nucleotide Sequence Database Collaboration (INSDC), comprising the GenBank, ENA (EMBL) and DDBJ databases. It includes organism names and taxonomic lineages for each of the sequences represented in the INSDC’s nucleotide and protein sequence databases. The taxonomy database is manually curated by a small group of scientists at the NCBI who use the current taxonomic literature to maintain a phylogenetic taxonomy for the source organisms represented in the sequence databases. The taxonomy database is a central organizing hub for many of the resources at the NCBI, and provides a means for clustering elements within other domains of NCBI web site, for internal linking between domains of the Entrez system and for linking out to taxon-specific external resources on the web. Our primary purpose is to index the domain of sequences as conveniently as possible for our user community.

## Usage

To use it in you sbt-project, add this to you `build.sbt`:

```scala
resolvers += "Era7 maven releases" at "http://releases.era7.com.s3.amazonaws.com"

libraryDependencies += "bio4j" %% "ncbi-taxonomy-module" % "0.2.0"
```
