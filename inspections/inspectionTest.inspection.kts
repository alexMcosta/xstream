import org.intellij.lang.annotations.Language
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiLocalVariable
import com.intellij.psi.PsiMethod

/**
 * This is an auto-generated template Java custom inspection
 * Reports all local variables inside all class methods
 *
 * The inspection is applied automatically and executed on-fly: to see the inspection results, open the Java file in the editor
 * 
 * In this example, the inspection algorithm is the following:
 *   1. Take all classes in file
 *   2. If the class is not an interface, take all its declared methods, otherwise ignore
 *   3. For each declared method, take all nodes that correspond to local variables and are descendants of method node 
 *   4. Reports variables' name and type
 */

/**
 * Full HTML description of inspection: Describe here motivation, examples, etc.
 */
@Language("HTML")
val htmlDescription = """
    <html>
    <body>
        HTML description of custom inspection
    </body>
    </html>
""".trimIndent()

/**
 * Inspection operates with file's PSI tree: to see the PSI tree of a file, open the PSI Viewer 
 * PSI tree is an AST representing file's source code with PsiFile is a root node 
 * You can traverse the tree/call API methods of specific PsiElements to retrieve some data

 * You can use the following utility methods to traverse the PSI tree from given PsiElement:
 * * PsiElement.getChildren() – all children nodes
 * * PsiElement.descendants() – all children nodes recursively (with children's children)
 * * PsiElement.descendantsOfType<...>() – all children of specified type recursively
 * * PsiElement.getParent() – parent node 
 * * PsiElement.parents(withSelf = false) – all parent nodes recursively
 * * PsiElement.siblings(forward = true, withSelf = false) – all forward siblings: nodes with the same parent located after this element
 * * PsiElement.siblings(forward = false, withSelf = false) – all backward siblings: nodes with the same parent located before this element
 
 * Call `inspection.findPsiFileByRelativeToProjectPath(String)` to get other PSI file 
 * Call `inspection.registerProblem(PsiElement, String)` function to report a problem from inspection
 * 
 * See the PSI Viewer for available APIs and PSI tree structure
 * Invoke "Open PSI Viewer" in the banner above or select "Tools | View PSI structure of Current File..." from the top menu
 * 
 * How to debug the inspection: call inspection.registerProblem(PsiElement, "your debug message") and see highlighting in the editor, also check PSI viewer
 */
val everyLocalVariableInMethodInspection = localInspection { psiFile, inspection ->
    // take all classes declared in file: all PsiClass children of PsiFile node (root)
    val classes = psiFile.descendantsOfType<PsiClass>()
    
    classes.forEach { javaClass: PsiClass ->
        // ignore interfaces
        if (javaClass.isInterface) {
            return@forEach
        }

        // take all declared methods of a class
        javaClass.methods.forEach { method: PsiMethod ->
            // take all local variables inside method
            val localVariables = method.descendantsOfType<PsiLocalVariable>()

            // for each local variable, report its name and type as a problem
            localVariables.forEach { variable: PsiLocalVariable ->
                val variableType = variable.type.canonicalText
                val message = "Thee} of type $variableType. Template custom inspection inspections/inspectionTest.inspection.kts"
                inspection.registerProblem(variable, message)
            }
        }
    }
}

// You can define multiple inspections in one .inspection.kts file 
listOf(
    InspectionKts(
        id = "InspectionTest", // inspection id (used in qodana.yaml)
        localTool = everyLocalVariableInMethodInspection,
        name = "Template custom inspection inspections/inspectionTest.inspection.kts", // Inspection name, displayed in UI
        htmlDescription = htmlDescription,
        level = HighlightDisplayLevel.WARNING,
    )
    // ...
)