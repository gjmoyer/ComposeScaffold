import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

fun customNavDrawerShape() = object : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f,0f,300f /* width */, size.height /* height */))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SandboxApp() {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val openNavDrawer = {
        scope.launch { scaffoldState.drawerState.open() }
    }

    val showModalBottomSheet = {
        scope.launch { sheetState.show() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column {
                Text(text = "Sheet Content")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Sheet Content")

            }
        },
    ) {
        Scaffold(scaffoldState = scaffoldState,
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen || scaffoldState.drawerState.isAnimationRunning,
            drawerContent = {
                Column(
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(text = "Drawer Content")
                }
            },
            drawerShape = customNavDrawerShape(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Compose Sandbox")
                    },
                    actions = {
                        IconButton(onClick = { showModalBottomSheet() }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart"
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { openNavDrawer() }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Text(text = "Scaffold Content")
            }
        }
    }
}