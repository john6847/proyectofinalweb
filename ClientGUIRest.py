from PyQt4 import Qt
from PyQt4 import QtGui,QtCore
import requests
from PyQt4.QtCore import QRect
from PyQt4.QtGui import QHBoxLayout
from PyQt4.QtGui import QIcon
from PyQt4.QtGui import QLabel
from PyQt4.QtGui import QLineEdit
from PyQt4.QtGui import QMainWindow
from PyQt4.QtGui import QWidget
from suds.client import Client
from suds.wsse import *
import json
from suds.sudsobject import asdict

URL = "http://localhost:4567/api/pastes/"

# username = ""
# password = ""

# from mainwindow import Ui_MainWindow
from PyQt4.QtGui import QTableWidget
from PyQt4.QtGui import QTableWidgetItem



class Login(QtGui.QDialog):
    def __init__(self, parent=None):

        super(Login, self).__init__(parent)
        self.resize(300,250)
        self.textName = QtGui.QLineEdit(self)
        self.textName.setPlaceholderText("enter your username")
        self.setFocus()
        self.show()

        self.textPass = QtGui.QLineEdit(self)
        self.textPass.setPlaceholderText("enter your password")
        self.textPass.setEchoMode(QtGui.QLineEdit.Password)
        self.setFocus()
        self.show()
        self.buttonLogin = QtGui.QPushButton('Login', self)
        self.buttonLogin.setStyleSheet("background-color: #C63D0F")
        self.buttonLogin.clicked.connect(self.handleLogin)



        layout = QtGui.QVBoxLayout(self)
        layout.addWidget(self.textName)
        layout.addWidget(self.textPass)
        layout.addWidget(self.buttonLogin)



    def handleLogin(self):
        global username
        global password
        username = self.textName.text()
        password = self.textPass.text()



        if username and password:
            self.accept()
        else:
            QtGui.QMessageBox.warning(
                self, 'Error', 'Bad user or password')


class Window(QtGui.QMainWindow):
    def __init__(self, parent=None):
        super(Window, self).__init__(parent)
        self.resize(300,200)
        self.move(QtGui.QApplication.desktop().screen().rect().center() - self.rect().center())

        self.buttonRest = QtGui.QPushButton('REST', self)
        self.buttonRest.setStyleSheet("background-color: #74AFAD")
        self.buttonRest.resize(200,150)
        x, y, w, h = 40, 100, 221, 30
        self.buttonRest.setGeometry(x,y,w,h)
        self.buttonRest.clicked.connect(self.handleButtonRest)

        self.setFocus()
        self.show()
        font = QtGui.QFont()
        font.setPointSize(18)
        font.setBold(True)
        # self.ui = Ui_MainWindow()
        # self.ui.setupUi(self)



    def handleButtonRest(self):

        response = requests.get(URL, headers={"Accept": "application/json"},
                                params={"username":str(username), "password":str(password)})
        self.dialog = showRest(response.json())
        self.dialog.show()

        #print response



global username
global password

class showRest (QtGui.QMainWindow):
    def __init__(self, response, parent=None):
        QtGui.QWidget.__init__(self, parent)
        self.resize(300, 200)
        self.move(QtGui.QApplication.desktop().screen().rect().center() - self.rect().center())

        self.response = response

        self.buttonListar = QtGui.QPushButton('Listar pastes', self)
        self.buttonListar.resize(200, 150)
        x, y, w, h = 40, 40, 221, 30
        self.buttonListar.setGeometry(x, y, w, h)
        self.buttonListar.setStyleSheet("background-color: #74AFAD")
        # self.buttonSoap.connect(self.createSoap)

        self.buttonListar.clicked.connect(self.listarPastes)


        self.buttonCrear = QtGui.QPushButton('Crear Paste', self)
        self.buttonCrear.setStyleSheet("background-color: #74AFAD")
        self.buttonCrear.resize(200, 150)
        x, y, w, h = 40, 100, 221, 30
        self.buttonCrear.setGeometry(x, y, w, h)
        self.buttonCrear.clicked.connect(self.crearPaste)
        # self.buttonSoap.connect(self.createSoap)

        self.setFocus()
        self.show()
        font = QtGui.QFont()
        font.setPointSize(18)
        font.setBold(True)

    def listarPastes(self):
        self.dialog = QtGui.QVBoxLayout()

        self.dialog.addWidget(QtGui.QLabel("Pastes"))
        self.table = QtGui.QTableWidget()
        self.dialog.addWidget(self.table)
        self.table.move(QtGui.QApplication.desktop().screen().rect().center() - self.rect().center())


        self.table.setColumnCount(9)
        self.table.setHorizontalHeaderItem(0,QtGui.QTableWidgetItem("Id"))
        self.table.setHorizontalHeaderItem(1,QtGui.QTableWidgetItem("Titulo"))
        self.table.setHorizontalHeaderItem(2,QtGui.QTableWidgetItem("URL"))
        self.table.setHorizontalHeaderItem(3,QtGui.QTableWidgetItem("Sintaxis"))
        self.table.setHorizontalHeaderItem(4,QtGui.QTableWidgetItem("Tipo Exposicion"))
        self.table.setHorizontalHeaderItem(5,QtGui.QTableWidgetItem("Fecha Expiracion"))
        self.table.setHorizontalHeaderItem(6,QtGui.QTableWidgetItem("Fecha Publicacion"))
        self.table.setHorizontalHeaderItem(7,QtGui.QTableWidgetItem("Cantidad Vista"))
        self.table.setHorizontalHeaderItem(8,QtGui.QTableWidgetItem("Bloque Codigo"))

        self.table.setColumnWidth(0, 80)
        self.table.setColumnWidth(1, 100)
        self.table.setColumnWidth(2, 200)
        self.table.setColumnWidth(3, 80)
        self.table.setColumnWidth(4, 150)
        self.table.setColumnWidth(5, 150)
        self.table.setColumnWidth(6, 150)
        self.table.setColumnWidth(7, 150)
        self.table.setColumnWidth(8, 300)

        self.table.show()
        count=0
        print self.response[0]["titulo"]
        self.table.setRowCount(len(self.response))
        for key in self.response:
            self.table.setItem(count,0,QtGui.QTableWidgetItem(str(key["id"])))
            self.table.setItem(count,1,QtGui.QTableWidgetItem(str(key["titulo"])))
            self.table.setItem(count,2,QtGui.QTableWidgetItem(str(key["url"])))
            self.table.setItem(count,3,QtGui.QTableWidgetItem(str(key["sintaxis"])))
            self.table.setItem(count,4,QtGui.QTableWidgetItem(str(key["tipoExposicion"])))
            self.table.setItem(count,5,QtGui.QTableWidgetItem(str(key["fechaExpiracion"])))
            self.table.setItem(count,6,QtGui.QTableWidgetItem(str(key["fechaPublicacion"])))
            self.table.setItem(count,7,QtGui.QTableWidgetItem(str(key["cantidadVista"])))
            self.table.setItem(count,8,QtGui.QTableWidgetItem(str(key["bloqueDeCodigo"])))
            self.table.setRowHeight(count,100)
            count = count+1



    def crearPaste(self):
        self.w = MyPopup()
        self.w.mostrar()
        self.w.setFixedWidth(600)
        self.w.setFixedHeight(500)
        self.w.show()


class MyPopup(QWidget):
    def __init__(self):
        self.form = None
        QWidget.__init__(self)

    def mostrar(self):
        label=QLabel(self)
        label.setGeometry(QtCore.QRect(10, 5, 50, 40))
        label.setText("Titulo")

        global editTextTitulo
        editTextTitulo= QtGui.QLineEdit(self)
        #left top
        editTextTitulo.setGeometry(QtCore.QRect(10, 35, 130, 27))

        labelBloque=QLabel(self)
        labelBloque.setGeometry(QtCore.QRect(10, 30, 150, 100))
        labelBloque.setText("Bloque de codigo")

        global editTextBloque
        #editTextBloque=QtGui.QPlainTextEdit(self)
        editTextBloque=QtGui.QTextEdit(self)

        #left top
        editTextBloque.setGeometry(QtCore.QRect(10, 90, 300, 400))

        label_3 = QtGui.QLabel(self)
        label_3.setGeometry(QtCore.QRect(350, 90, 101, 21))
        label_3.setText("Sintaxis")

        global comboBoxSintaxis
        comboBoxSintaxis= QtGui.QComboBox(self)
        comboBoxSintaxis.setGeometry(QtCore.QRect(350, 110, 131, 24))

        list1 = [
            self.tr('Select One'),
            self.tr('apache'),
            self.tr('c#'),
            self.tr('bash'),
            self.tr('c++'),
            self.tr('css'),
            self.tr('coffeeScript'),
            self.tr('diff'),
            self.tr('html'),
            self.tr('xml'),
            self.tr('http'),
            self.tr('ini'),
            self.tr('json'),
            self.tr('javascript'),
            self.tr('lisp'),
            self.tr('makefile'),
            self.tr('markdown'),
            self.tr('objective-C'),
            self.tr('perl'),
            self.tr('python'),
            self.tr('ruby'),
            self.tr('sql'),
            self.tr('session'),
            self.tr('arduino'),
            self.tr('arm assembler'),
            self.tr('clojure'),
            self.tr('excel'),
            self.tr('f#'),
            self.tr('go'),
            self.tr('haskell'),
            self.tr('groovy'),
            self.tr('r'),
            self.tr('sml'),
            self.tr('swift'),
            self.tr('vb.net'),
            self.tr('yaml'),
        ]

        comboBoxSintaxis.clear()
        for text in list1:
            comboBoxSintaxis.addItem(text)

        label_4 = QtGui.QLabel(self)
        label_4.setGeometry(QtCore.QRect(350, 150, 150, 21))
        label_4.setText("Tiempo expiracion")

        global comboBoxTiempo
        comboBoxTiempo= QtGui.QComboBox(self)
        comboBoxTiempo.setGeometry(QtCore.QRect(350, 170, 131, 24))

        list2 = [
            self.tr('Select One'),
            self.tr('never'),
            self.tr('10 minutes'),
            self.tr('15 minutes'),
            self.tr('30 minutes'),
            self.tr('1 hour'),
            self.tr('1 day'),
            self.tr('1 week'),
        ]

        comboBoxTiempo.clear()
        for text in list2:
            comboBoxTiempo.addItem(text)

        label_5 = QtGui.QLabel(self)
        label_5.setGeometry(QtCore.QRect(350, 210, 101, 21))
        label_5.setText("Tipo exposicion")

        global comboBoxTipo
        comboBoxTipo= QtGui.QComboBox(self)
        comboBoxTipo.setGeometry(QtCore.QRect(350, 230, 131, 24))

        list3 = [
            self.tr('Select One'),
            self.tr('public'),
            self.tr('private'),
            self.tr('unlisted'),
        ]

        comboBoxTipo.clear()
        for text in list3:
            comboBoxTipo.addItem(text)

        botonEnviar=QtGui.QPushButton(self)
        botonEnviar.setText("Publicar")
        botonEnviar.setGeometry(QtCore.QRect(350, 400, 131, 24))
        self.connect(botonEnviar, Qt.SIGNAL("clicked()"), publicar)



def publicar():
    titulo = editTextTitulo.text()
    bloqueCodigo=editTextBloque.toPlainText()

    sintaxis = str(comboBoxSintaxis.currentText())
    tiempoExposicion = str(comboBoxTiempo.currentText())
    tipoExposicion = str(comboBoxTipo.currentText())


    print bloqueCodigo
    # payload={"bloqueDeCodigo": bloqueCodigo, "sintaxis":sintaxis,"titulo": titulo,
    #         "fechaExpiracion":tiempoExposicion, "tipoExposicion": tipoExposicion,
    #         "username":str(username), "password": str(password)}

    if 'username' in globals() and 'password' in globals():
        paste = requests.post("http://localhost:4567/api/pastes/save", headers={"Accept": "application/json"}
        , params={"bloqueDeCodigo": str(bloqueCodigo), "sintaxis":sintaxis,"titulo": str(titulo),
                "fechaExpiracion":tiempoExposicion, "tipoExposicion": tipoExposicion,
                "username":str(username), "password": str(password)})
    else:
        if 'username' in globals() and 'password' in globals():
            paste = requests.post("http://localhost:4567/api/pastes/save", headers={"Accept": "application/json"}
                                  , params={"bloqueDeCodigo": str(bloqueCodigo), "sintaxis": sintaxis,
                                            "titulo": str(titulo),
                                            "fechaExpiracion": tiempoExposicion, "tipoExposicion": tipoExposicion
                                            })

    print paste.url


if __name__ == '__main__':

    import sys
    app = QtGui.QApplication(sys.argv)
    login = Login()


    if login.exec_() == QtGui.QDialog.Accepted:
        window = Window()
        window.show()
        # table = MyTable(mystruct, 5, 3)
        # table.show()
        sys.exit(app.exec_())