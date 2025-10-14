import type { FormRules } from 'element-plus'

export class ValidationFactory {
  static createRules(type: string): FormRules {
    switch(type) {
      case 'Coordinate':
        return this.coordinateRules()
      case 'Location':
        return this.locationRules()
      case 'DragonHead':
        return {}
      default:
        return {}
    }
  }

  private static locationRules(): FormRules {
    return {
      x: [
        { required: true, message: 'X coordinate is required', trigger: 'blur' },
      ],
      z: [
        { required: true, message: 'Z coordinate is required', trigger: 'blur' },
      ],
      name: [
        { required: true, message: 'Z coordinate is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value.length > 240) {
              callback(new Error('Name length must be less than 241'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
    }
  }

  private static coordinateRules(): FormRules {
    return {
      x: [
        { required: true, message: 'X coordinate is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value < -999) {
              callback(new Error('X must be greater than -999'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      y: [
        { required: true, message: 'Y is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value >845) {
              callback(new Error('Y must be less than 845'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ]
    }
  }

}