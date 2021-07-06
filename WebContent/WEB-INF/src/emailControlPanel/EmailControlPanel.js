import Quill from "quill";

export default class EmailControlPanel {
    static emailTypes = [
        'acceptpaid',
        'acceptfree',
        'rejected',
        'waitlist',
        'registered',
        'enrolledpaid',
        'enrolledfree',
        'withdraw'
    ];

    static toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        [{'header': 1}, {'header': 2}],               // custom button values
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
        [{'header': [1, 2, 3, 4, 5, 6, false]}],
    ];

    constructor() {
        const typeToEditorMap = new Map();
        EmailControlPanel.emailTypes.forEach((emailType) => {
            const editorID = '#' + emailType + '-editor';
            const quillEditor = new Quill(editorID, {
                modules: {
                    toolbar: EmailControlPanel.toolbarOptions
                },
                theme: 'snow'
            });
            typeToEditorMap.set(emailType, quillEditor);
        });

        Array
            .from(document.getElementsByClassName("save-email-button"))
            .forEach(element => {
                element.addEventListener("click", (event) => {
                    const emailType = event.target.name.split("-")[1];
                    const hiddenInput = document.getElementById(`hidden-input-${emailType}`);
                    hiddenInput.value = typeToEditorMap.get(emailType).root.innerHTML;
                });
            })
    }
}