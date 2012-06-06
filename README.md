# ProofProcess framework

A framework to capture, store and analyse expert's interactive proof process. The framework is part of the [AI4FM research project][ai4fm], which aims to learn from an expert doing interactive theorem proving to increase automation of formal proofs.

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
