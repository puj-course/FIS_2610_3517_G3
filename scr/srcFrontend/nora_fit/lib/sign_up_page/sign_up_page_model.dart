import '/flutter_flow/flutter_flow_util.dart';
import '/index.dart';
import 'sign_up_page_widget.dart' show SignUpPageWidget;
import 'package:flutter/material.dart';

class SignUpPageModel extends FlutterFlowModel<SignUpPageWidget> {
  ///  State fields for stateful widgets in this page.

  // State field(s) for TextFieldPassword widget.
  FocusNode? textFieldPasswordFocusNode;
  TextEditingController? textFieldPasswordTextController;
  late bool textFieldPasswordVisibility;
  String? Function(BuildContext, String?)?
      textFieldPasswordTextControllerValidator;
  // State field(s) for TextFieldEmail widget.
  FocusNode? textFieldEmailFocusNode;
  TextEditingController? textFieldEmailTextController;
  String? Function(BuildContext, String?)?
      textFieldEmailTextControllerValidator;
  // State field(s) for TextFieldConfirmPassword widget.
  FocusNode? textFieldConfirmPasswordFocusNode;
  TextEditingController? textFieldConfirmPasswordTextController;
  late bool textFieldConfirmPasswordVisibility;
  String? Function(BuildContext, String?)?
      textFieldConfirmPasswordTextControllerValidator;
  // State field(s) for TextFieldUsername widget.
  FocusNode? textFieldUsernameFocusNode;
  TextEditingController? textFieldUsernameTextController;
  String? Function(BuildContext, String?)?
      textFieldUsernameTextControllerValidator;

  @override
  void initState(BuildContext context) {
    textFieldPasswordVisibility = false;
    textFieldConfirmPasswordVisibility = false;
  }

  @override
  void dispose() {
    textFieldPasswordFocusNode?.dispose();
    textFieldPasswordTextController?.dispose();

    textFieldEmailFocusNode?.dispose();
    textFieldEmailTextController?.dispose();

    textFieldConfirmPasswordFocusNode?.dispose();
    textFieldConfirmPasswordTextController?.dispose();

    textFieldUsernameFocusNode?.dispose();
    textFieldUsernameTextController?.dispose();
  }
}
