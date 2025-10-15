import type { FormRules } from 'element-plus'
import { DateTime } from 'luxon';

export class ValidationFactory {
  static createRules(type: string): FormRules {
    switch(type) {
      case 'Coordinate':
        return this.coordinateRules()
      case 'Location':
        return this.locationRules()
      case 'DragonHead':
        return {}
      case 'DragonCave':
        return this.dragoncaveRules()
      case 'Person':
        return this.personRules()
      default:
        return {}
    }
  }

  private static personRules(): FormRules {
    return {
      name: [
        { required: true, message: 'Name is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value.length > 0) {
              callback(new Error('Name must be not blank'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      hairColor: [
        { required: true, message: 'HairColor is required', trigger: 'blur' },
      ],
      birthday: [
        { 
          validator: (rule, value: DateTime, callback) => {
            if (value && (DateTime.now() < value)) {
              callback(new Error('Date must be in past'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
      height: [
        { required: true, message: 'Height is required', trigger: 'blur' },
        { min: 1, message: 'Height must be grater than 0', trigger: 'blur' },
      ],
      weight: [
        { min: 1, message: 'Weight must be grater than 0', trigger: 'blur' },
      ],
      passportId: [
        { required: true, message: 'Passport ID is required', trigger: 'blur' },
        { 
          validator: (rule, value, callback) => {
            if (value.length > 23) {
              callback(new Error('Passport ID must be less than 23 symbols'))
            } else {
              callback()
            }
          }, 
          trigger: 'change' 
        }
      ],
    }
  }

  private static dragoncaveRules(): FormRules {
    return {
      depth: [
        { required: true, message: 'Depth is required', trigger: 'blur' },
      ],
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
        { required: true, message: 'Name is required', trigger: 'blur' },
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
        { required: true, message: 'Y coordinate is required', trigger: 'blur' },
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