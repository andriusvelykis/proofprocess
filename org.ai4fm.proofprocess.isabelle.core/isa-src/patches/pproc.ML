(*  Title:      Pure/Tools/pproc.ML
    Author:     Andrius Velykis

Proof process support -- tracing proof information to read outside Isabelle.
*)

signature PPROC_SIG =
sig
  val trace_term_insts: (Token.T * (indexname * 'a)) list -> (indexname * term) list -> unit
end;


structure PProc: PPROC_SIG =
struct

(** tracing instantiations **)

local

val new_id = Synchronized.counter();

fun find_token mixed_insts iname = 
  Option.map (op fst) (List.find (fn (_, (index, _)) => index = iname) mixed_insts);
  
fun inst_token mixed_insts term_inst =
  Option.map (fn token => (token, term_inst, new_id())) (find_token mixed_insts (fst term_inst));
  
fun inst_tokens mixed_insts term_insts = List.mapPartial (inst_token mixed_insts) term_insts;

fun inst_markup id = Markup.properties [("trace_id", (Markup.print_int id))] ("inst", []);

fun inst_trace value_xml ((name, index), value) id =
  XML.Elem((Markup.properties ([("index", (Markup.print_int index))]) (Markup.name name (inst_markup id))), (value_xml value));
  
fun report_inst inst_value_xml (token, inst, id) = (
  tracing(YXML.string_of (inst_trace inst_value_xml inst id));
  Position.report (Token.position_of token) (inst_markup id));

in

fun trace_term_insts mixed_insts term_insts =
  List.app (report_inst Term_XML.Encode.term) (inst_tokens mixed_insts term_insts)

end;
end;
