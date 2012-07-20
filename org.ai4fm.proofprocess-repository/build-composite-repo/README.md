# Create composite p2 repository

[Composite p2 repositories][p2-comp] allow aggregating content from multiple p2
repositories. This way we can include dependencies to the ProofProcess plug-ins
in the same repository. Thus the end users do not need to collect all dependencies
manually - they can all be retrieved from the update site.

[p2-comp]: http://wiki.eclipse.org/Equinox/p2/Composite_Repositories_(new)

## Running the script

The `comp-repo.sh` shell script builds (or modifies) a composite p2 repository.
Below is a sample execution that produces the 'latest' composite repository
that contains the ProofProcess update site together with its main dependencies.

The assumption here is that this composite repository is placed under
`http://www.ai4fm.org/proofprocess/updates/latest`
and the actual ProofProcess repository is under
`http://www.ai4fm.org/proofprocess/updates/latest/proofprocess`.


    ./comp-repo.sh <REPO_DIR> --eclipse <ECLIPSE_DIR> \
    --name "ProofProcess Framework p2 Composite Repository" \
    add http://www.ai4fm.org/proofprocess/updates/latest/proofprocess \
    add https://hudson.eclipse.org/hudson/job/gef-zest-integration/lastSuccessfulBuild/artifact/targetPlatform \
    add http://www.ai4fm.org/isabelle-eclipse/updates/latest \
    add http://czt.sourceforge.net/eclipse/updates/latest

See [the blog post][comp-repo-blog] for more details on the script and its arguments.

[comp-repo-blog]: http://eclipsesource.com/blogs/2012/06/11/creating-p2-composite-repositories-on-the-command-line/

