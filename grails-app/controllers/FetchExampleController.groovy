
import fetchexample.FetchExample
import metafunctionality.Module
import metafunctionality.ModuleOutput


class FetchExampleController {

    def start() {
        String inputID = Module.findByModuleId(params.id).inputID
        FetchExample input = FetchExample.findByModuleDataID(inputID)
        String word = input.word
        List<String> rc = input.rhymingCandidates
        rc.add(input.answer)
        //rc.toSet()
        [rc:rc, word:word, modID: params.id]
    }

    def submit() {

        List<String> valueRows = new ArrayList<String>()
        ModuleOutput output = new ModuleOutput()
        output.headers = ["word_selected", "correct_answer", "result"]
        String selected = params.candidates

        String inputID = Module.findByModuleId(params.modID).inputID
        FetchExample input = FetchExample.findByModuleDataID(inputID)

        String answer = input.answer
        String valueRow = selected + "," + answer + ","
        if (selected.equals(answer)) {
            valueRow += "correct"
        } else {
            valueRow += "incorrect"
        }
        valueRows.add(valueRow)
        output.valueRows = valueRows
        Module m = Module.findByModuleId(params.modID)
        if (m.outputIDs != null) {
            m.outputIDs.add(output.moduleDataID)
        } else {
            m.outputIDs = [output.moduleDataID]
        }
        output.type = "FetchExample"
        m.isCompleted = true
        m.save(flush: true)
        output.save(flush: true)
        render(view: "submit")
        //redirect(controller: "appforliteracy.FileOutput", action: "output", params: [id: output.moduleDataID])

    }

    def logout() {
        redirect(controller:"Login", action:"index")
    }

}
