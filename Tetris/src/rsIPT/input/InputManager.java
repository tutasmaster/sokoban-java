package rsIPT.input;

import java.util.*;

public class InputManager {
    public void addKey(String key) {
        _input_states.put(key, InputState.Depressed);
    }

    public void updateKey(String key, InputState i){
        if(_input_states.get(key) != null){
            _input_states.put(key,i);
        }
    }

    public void iterate(){
        Set<Map.Entry<String, InputState>> m3 = _input_states.entrySet();
        for(Map.Entry<String, InputState> set : m3){
            InputState i = set.getValue();
            if(i == InputState.Pressed){
                set.setValue(InputState.Held);
            }else if(i == InputState.Depressed){
                set.setValue(InputState.Released);
            }
        }
    }

    public InputState getKey(String s){
        return _input_states.get(s);
    }

    public enum InputState{
        Depressed,
        Released,
        Pressed,
        Held
    }
    private HashMap<String, InputState> _input_states = new HashMap<>();
}
