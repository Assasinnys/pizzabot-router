import com.itechart.pizzabotrouter.*
import com.itechart.pizzabotrouter.util.ERR_BAD_INPUT
import com.itechart.pizzabotrouter.util.ERR_BAD_POS
import com.itechart.pizzabotrouter.util.ERR_INPUT_EMPTY
import com.itechart.pizzabotrouter.util.ERR_WRONG_BRACES
import org.junit.jupiter.api.Test

class AlgorithmTest {

    private val pizzabotRouter = PizzabotRouter()

    @Test
    fun `case 1`() {
        assert(pizzabotRouter.findRouteToClients("5x5 (1, 3) (4, 4)") == "ENNNDEEEND")
    }

    @Test
    fun `case 2`() {
        assert(pizzabotRouter.findRouteToClients("5x5 (1, 4) (2, 2)(3, 1)") == "EENNDESDWWNNND")
    }

    @Test
    fun `case 3`() {
        assert(pizzabotRouter.findRouteToClients("5x5 (0,0)(1,3)(4,4)(4,2)(4,2)(0,1)(3,2)(2,3)(4,1)") == "DNDENNDEESDWNDEESSDNDDNND")
    }

    @Test
    fun `error case 1`() {
        assert(pizzabotRouter.findRouteToClients("5x5 (1.)(2, 3)") == ERR_BAD_POS)
    }

    @Test
    fun `error case 2`() {
        assert(pizzabotRouter.findRouteToClients("5x5(1.3)(2, 3)") == ERR_BAD_POS)
    }

    @Test
    fun `error case 3`() {
        assert(pizzabotRouter.findRouteToClients("5x5 (1,) (1, 1)") == ERR_BAD_POS)
    }

    @Test
    fun `error case 4`() {
        assert(pizzabotRouter.findRouteToClients("5x5") == ERR_BAD_INPUT)
    }

    @Test
    fun `error case 5`() {
        assert(pizzabotRouter.findRouteToClients("") == ERR_INPUT_EMPTY)
    }

    @Test
    fun `error case 6`() {
        assert(pizzabotRouter.findRouteToClients("5x5 (2, 1 (1, 1)") == ERR_WRONG_BRACES)
    }
}