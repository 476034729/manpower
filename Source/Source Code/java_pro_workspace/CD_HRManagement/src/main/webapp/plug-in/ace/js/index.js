$(document).ready(function () {
    const $selectBox = $(".form_project");
    const $option = $('.form_project_option');

    const serviceRoot = "ProjectCodeController.do?getProjectListByCodeContact";
    const subDataSource = {
        schema: {
            model: {
                id: "code_contact",
                hasChildren: 'has_son'
            }
        },
        transport: {
            read: {
                url: function (options) {
                    return `${serviceRoot}&codeContact=${options['code_contact']}`;
                }
            }
        }
    };
    const projectDataSource = new kendo.data.HierarchicalDataSource({
        transport: {
            read: {
                url: serviceRoot,
                dataType: 'json',
            }
        },
        schema: {
            model: {
                id: "code_contact",
                hasChildren: 'has_son',
                children: subDataSource
            }
        }
    });
    $("#treeview").kendoTreeView({
        dataSource: projectDataSource,
        dataTextField: "project_name",
        select(e) {
            const getParentSource = (node = e.node) => {
                if (this.dataItem(node.parentNode)) {
                    return getParentSource(node.parentNode);
                }
                return this.dataItem(node);
            }
            const topSource = getParentSource(e.node);

            codeContact = topSource['code_contact'];
            $(".form_project_input").val(e.node.innerText);
            $option.hide();
        }
    });

    $selectBox.click(function(event) {
        event.stopPropagation();
    });
    $('.form_project_input').focus(function (event) {
        $option.show();
    })
    $(document).click(function(event) {
        var eo = $(event.target);
        if (
            $selectBox.is(":visible")
            && eo.attr("class") !== "option"
            && !eo.parent(".option").length
        ) {
            $option.hide();
        }
    });
})
