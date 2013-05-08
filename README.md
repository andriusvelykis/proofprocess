# ProofProcess framework

A framework to capture, store and analyse expert's interactive proof process. The framework is part of the [AI4FM research project][ai4fm], which aims to learn from an expert doing interactive theorem proving to increase automation of formal proofs.

## Downloads

Install the ProofProcess framework plug-ins by using the following update site in the Eclipse [_Update Manager_][update-manager]:

[`http://www.ai4fm.org/proofprocess/updates/nightly/`][download-updates]

When installing, select the ProofProcess framework for your theorem prover, e.g. **Isabelle ProofProcess integration** or **Z/EVES ProofProcess integration** - all required plug-ins will be downloaded and installed automatically.

Note that Isabelle/Eclipse requires **Java 7** to run, so make sure that your Eclipse IDE is running on Java 7. [Refer to Isabelle/Eclipse documentation for details][isabelle-eclipse-java7].

[download-updates]: http://www.ai4fm.org/proofprocess/updates/nightly/
[update-manager]: http://www.vogella.com/articles/Eclipse/article.html#updatemanager
[isabelle-eclipse-java7]: http://andriusvelykis.github.io/isabelle-eclipse/getting-started/#java-7


## Components

The framework aims to be generic across theorem provers. Current prototype implementations include support for Isabelle and Z/EVES theorem provers.

### ProofProcess core

The core of the framework is currently based on the [Eclipse platform][eclipse]. We are using [EMF][emf] to model and represent the proof process. The code is developed as a mixed Java/Scala solution, therefore [Scala IDE][scala-ide] is required to build it.

### Isabelle integration

Prototype implementation with [Isabelle][isabelle] proof assistant is available. Currently it requires [Isabelle/Eclipse IDE][isabelle-eclipse] (source code available on [GitHub][isabelle-eclipse]).

The Isabelle/ProofProcess plug-ins are named `org.ai4fm.proofprocess.isabelle.*`.

### Z/EVES integration

Prototype implementation with [Z/EVES][zeves] proof assistant is available. The integration is based on and requires [Community Z Tools (CZT)][czt] link with Z/EVES. It is available from [CZT repository][czt-repo] (not included in the official 1.5 release).

The Isabelle/ProofProcess plug-ins are named `org.ai4fm.proofprocess.zeves.*`.


[ai4fm]: http://www.ai4fm.org
[eclipse]: http://www.eclipse.org
[emf]: http://www.eclipse.org/modeling/emf/
[scala-ide]: http://scala-ide.org/
[isabelle]: http://www.cl.cam.ac.uk/research/hvg/isabelle/
[isabelle-eclipse]: http://github.com/andriusvelykis/isabelle-eclipse
[zeves]: http://oracanada.com/z-eves/welcome.html
[czt]: http://czt.sourceforge.net/
[czt-repo]: http://sourceforge.net/projects/czt/develop
