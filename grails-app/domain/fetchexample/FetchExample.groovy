package fetchexample

import metafunctionality.ModuleInput

class FetchExample extends ModuleInput {
    String word
    String answer
    static hasMany = [rhymingCandidates:String]
    List rhymingCandidates
}
