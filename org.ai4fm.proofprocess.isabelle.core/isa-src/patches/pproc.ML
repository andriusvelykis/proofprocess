(*  Title:      Pure/Tools/pproc.ML
    Author:     Andrius Velykis

Proof process support -- tracing proof information to read outside Isabelle.
*)

signature PPROC_SIG =
sig
  val trace_inst_names: (Token.T * (indexname * 'a)) list -> unit
  val trace_cmd_terms: string list -> term list -> unit
end;


structure PProc: PPROC_SIG =
struct

local

(** tracing instantiations **)

fun inst_markup ((name, index), _) =
  (Markup.properties ([("index", (Markup.print_int index))]) (Markup.name name ("inst", [])));

fun report_inst (token, inst) =
  Position.report (Token.position_of token) (inst_markup inst);

(** tracing command terms **)

fun encode_term term = XML.Elem(("term", []), Term_XML.Encode.term term)

fun encode_term_source str = XML.Elem(("source", []), [XML.Text(str)])

fun cmd_term (str, term) = XML.Elem(("cmd_term", []), [encode_term_source str, encode_term term]);

fun cmd_terms strs terms = XML.Elem(("cmd_terms", []), map cmd_term (ListPair.zip (strs, terms)));

in

fun trace_inst_names mixed_insts =
  List.app report_inst mixed_insts;

fun trace_cmd_terms strs terms =
  tracing(YXML.string_of (cmd_terms strs terms));

end;
end;
